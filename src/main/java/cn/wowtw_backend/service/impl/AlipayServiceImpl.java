package cn.wowtw_backend.service.impl;

import cn.wowtw_backend.model.Order;
import cn.wowtw_backend.service.AlipayService;
import cn.wowtw_backend.utils.OrderUtil;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AlipayServiceImpl implements AlipayService {

    private final AlipayConfig alipayConfig;
    public AlipayServiceImpl(AlipayConfig alipayConfig) {
        this.alipayConfig = alipayConfig;
    }

    @Override
    public String preCreate(Order createOrder) throws AlipayApiException {
        String outTradeNo = OrderUtil.generateOrderNo();
        String inviteIdentifier = createOrder.getInviteIdentifier();
        String totalAmount = inviteIdentifier == null || inviteIdentifier.isEmpty() ? "345.00" : "315.00";

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
        /*
        String httpBody = response.getBody();
        JsonObject jsonObject = JsonParser.parseString(httpBody).getAsJsonObject();
        String alipayQRCode = jsonObject.getAsJsonObject("alipay_trade_precreate_response").get("qr_code").getAsString();
        */
        System.out.println(alipayQRCode);

        if (response.isSuccess()) {
            System.out.println("调用成功");
            return alipayQRCode;
        } else {
            System.out.println("调用失败");
            /*
            sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            System.out.println(diagnosisUrl);
            */
        }
        return alipayQRCode;
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


        // String httpBody = response.getBody();
        // JsonObject jsonObject = JsonParser.parseString(httpBody).getAsJsonObject();
        // String tradeStatus = jsonObject.getAsJsonObject("alipay_trade_query_response").get("trade_status").getAsString();

        if (response.isSuccess()) {
            System.out.println("查询成功，订单状态为:" + response.getTradeStatus());
            return response.getTradeStatus();
        } else {
            System.out.println("查询失败");
            return null;
        }
    }

}
