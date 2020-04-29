package top.tinn.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @Description OmsMoneyInfoParam 修改订单费用信息参数
 * @Author Tinn
 * @Date 2020/4/9 14:45
 */
@Getter
@Setter
public class OmsMoneyInfoParam {
    private Long orderId;
    private BigDecimal freightAmount;
    private BigDecimal discountAmount;
    private Integer status;
}
