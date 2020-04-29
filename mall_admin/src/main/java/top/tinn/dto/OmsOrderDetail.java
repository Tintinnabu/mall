package top.tinn.dto;

import lombok.Getter;
import lombok.Setter;
import top.tinn.model.OmsOrder;
import top.tinn.model.OmsOrderItem;
import top.tinn.model.OmsOrderOperateHistory;

import java.util.List;

/**
 * @Description OmsOrderDetail
 * @Author Tinn
 * @Date 2020/4/9 14:44
 */
public class OmsOrderDetail extends OmsOrder {
    @Getter
    @Setter
    private List<OmsOrderItem> orderItemList;
    @Getter
    @Setter
    private List<OmsOrderOperateHistory> historyList;
}
