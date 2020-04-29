package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.model.OmsOrderOperateHistory;

import java.util.List;

public interface OmsOrderOperateHistoryDao {
    int insertList(@Param("list") List<OmsOrderOperateHistory> orderOperateHistoryList);
}
