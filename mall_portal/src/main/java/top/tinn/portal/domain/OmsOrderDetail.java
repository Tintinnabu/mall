package top.tinn.portal.domain;

import lombok.Getter;
import lombok.Setter;
import top.tinn.model.OmsOrder;
import top.tinn.model.OmsOrderItem;

import java.util.List;

/**
 * @Description OmsOrderDetail
 * @Author Tinn
 * @Date 2020/4/10 16:21
 */
public class OmsOrderDetail extends OmsOrder {
    @Getter
    @Setter
    private List<OmsOrderItem> orderItemList;
}
