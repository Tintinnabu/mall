package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.dto.OmsOrderDeliveryParam;
import top.tinn.dto.OmsOrderDetail;
import top.tinn.dto.OmsOrderQueryParam;
import top.tinn.model.OmsOrder;

import java.util.List;

public interface OmsOrderDao {
    /**
     * 条件查询订单
     */
    List<OmsOrder> getList(@Param("queryParam") OmsOrderQueryParam queryParam);
    /**
     * 批量发货
     */
    int delivery(@Param("list") List<OmsOrderDeliveryParam> deliveryParamList);

    /**
     * 获取订单详情
     */
    OmsOrderDetail getDetail(@Param("id") Long id);
}
