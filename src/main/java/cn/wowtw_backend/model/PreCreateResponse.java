package cn.wowtw_backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PreCreateResponse {
    private String alipayQRCode;
    private String outTradeNo;
}
