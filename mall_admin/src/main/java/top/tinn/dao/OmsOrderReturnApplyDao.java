package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.dto.OmsOrderReturnApplyResult;
import top.tinn.dto.OmsReturnApplyQueryParam;
import top.tinn.model.OmsOrderReturnApply;

import java.util.List;

/**
 * 订单退货申请自定义Dao
 */
public interface OmsOrderReturnApplyDao {
    /**
     * 查询申请列表
     */
    List<OmsOrderReturnApply> getList(@Param("queryParam") OmsReturnApplyQueryParam queryParam);

    /**
     * 获取申请详情
     */
    OmsOrderReturnApplyResult getDetail(@Param("id") Long id);
}
