package cn.wowtw_backend.service.impl;

import cn.wowtw_backend.model.AlipayOrder;
import cn.wowtw_backend.repository.AlipayOrderRepository;
import cn.wowtw_backend.service.AlipayService;
import cn.wowtw_backend.utils.OrderUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeCancelModel;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class AlipayServiceImpl implements AlipayService {

    private final AlipayConfig alipayConfig;
    private final AlipayOrderRepository alipayOrderRepository;

    // 存储tradeStatus查询结果，方便共享
    private final ConcurrentHashMap<String, String> pollResults = new ConcurrentHashMap<>();


    @Override
    public AlipayOrder preCreateOrder(AlipayOrder preCreateRequest) throws AlipayApiException {
        String outTradeNo = OrderUtil.generateOrderNo();
        String phoneNumber = preCreateRequest.getPhoneNumber();
        String inviteIdentifier = preCreateRequest.getInviteIdentifier();
        String totalAmount = inviteIdentifier == null || inviteIdentifier.isEmpty() ? "0.03" : "0.01";

        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);

        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        model.setOutTradeNo(outTradeNo);
        model.setTotalAmount(totalAmount);
        model.setSubject("wowtw.cn会员");
        request.setBizModel(model);

        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());

        String alipayQRCode = response.getQrCode();
        System.out.println(alipayQRCode);

        // Save alipay order to database
        AlipayOrder preCreateOrder = new AlipayOrder();
        preCreateOrder.setOutTradeNo(outTradeNo);
        preCreateOrder.setAlipayQRCode(alipayQRCode);
        preCreateOrder.setPhoneNumber(phoneNumber);
        preCreateOrder.setInviteIdentifier(inviteIdentifier);
        preCreateOrder.setTotalAmount(new BigDecimal(totalAmount));
        preCreateOrder.setStatus("CREATED");
        preCreateOrder.setCreateTime(LocalDateTime.now());
        preCreateOrder.setUpdateTime(LocalDateTime.now());
        alipayOrderRepository.save(preCreateOrder);


        if (response.isSuccess()) {
            System.out.println("调用预创订单接口成功");
            return preCreateOrder;
        } else {
            System.out.println("调用预创订单接口失败");
            /*
            sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            System.out.println(diagnosisUrl);
            */
            return null;
        }
    }

    @Override
    public String queryAlipayResult(String outTradeNo) throws AlipayApiException {
        // 初始化SDK
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);

        // 构造请求参数调用接口
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();

        // 设置订单支付时传入的商户订单号，预创订单后返回给前端，前端再传过来？
        model.setOutTradeNo(outTradeNo);
        request.setBizModel(model);

        AlipayTradeQueryResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());

        if (response.isSuccess()) {
            System.out.println("查询成功，订单状态为:" + response.getTradeStatus());
            return response.getTradeStatus();
        } else {
            System.out.println("查询失败");
            return response.getSubCode();
        }
    }

    // 支付状态轮询
    @Override
    public void pollPayment(String outTradeNo) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        scheduler.scheduleAtFixedRate(new Runnable() {
            int attempts = 0;
            final int maxAttempts = 17;

            @Override
            public void run() {
                attempts ++;
                try {
                    String tradeStatus = queryAlipayResult(outTradeNo);
                    if ("TRADE_SUCCESS".equals(tradeStatus)) {
                        System.out.println("交易成功");
                        pollResults.put(outTradeNo, tradeStatus);
                        scheduler.shutdown();
                    } else if ("TRADE_CLOSED".equals(tradeStatus)) {
                        System.out.println("交易关闭");
                        scheduler.shutdown();
                    } else if ("WAIT_BUYER_PAY".equals(tradeStatus) || attempts >= maxAttempts) {
                        cancelPayment(outTradeNo);
                        System.out.println("超时未支付，交易已撤销");
                        pollResults.put(outTradeNo, "TRADE_CLOSED");
                        scheduler.shutdown();
                    }
                } catch (AlipayApiException e) {
                    e.printStackTrace();
                    scheduler.shutdown();
                }
            }
        }, 2, 5, TimeUnit.SECONDS);
    }

    @Override
    public void cancelPayment(String outTradeNo) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig);

        AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();
        AlipayTradeCancelModel model = new AlipayTradeCancelModel();
        model.setOutTradeNo(outTradeNo);
        request.setBizModel(model);

        AlipayTradeCancelResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());

        if (response.isSuccess()) {
            System.out.println("调用取消订单接口成功，已取消订单");
        } else {
            System.out.println("调用取消订单接口失败");
        }
    }

    public String getPollResult(String outTradeNo) {
        return pollResults.get(outTradeNo);
    }
}
