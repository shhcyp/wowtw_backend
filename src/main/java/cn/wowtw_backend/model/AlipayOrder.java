package cn.wowtw_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_orders")
@NoArgsConstructor
@AllArgsConstructor
public class AlipayOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "out_trade_no", nullable = false, unique = true, length = 64)
    private String outTradeNo;

    @Column(name = "alipay_qr_code", length = 255)
    private String alipayQRCode;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "invite_identifier", length = 64)
    private String inviteIdentifier;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "create_time", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createTime;

    @Column(name = "update_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updateTime;
}
