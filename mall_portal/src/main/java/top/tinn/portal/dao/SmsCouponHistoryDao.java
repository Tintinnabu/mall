package top.tinn.portal.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.portal.domain.SmsCouponHistoryDetail;

import java.util.List;

public interface SmsCouponHistoryDao {
    List<SmsCouponHistoryDetail> getDetailList(@Param("memberId") Long memberid);
}
