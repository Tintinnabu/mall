package top.tinn.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description OmsReceiverInfoParam 订单修改收货人信息参数
 * @Author Tinn
 * @Date 2020/4/9 14:44
 */
@Getter
@Setter
public class OmsReceiverInfoParam {
    private Long orderId;
    private String receiverName;
    private String receiverPhone;
    private String receiverPostCode;
    private String receiverDetailAddress;
    private String receiverProvince;
    private String receiverCity;
    private String receiverRegion;
    private Integer status;
}
