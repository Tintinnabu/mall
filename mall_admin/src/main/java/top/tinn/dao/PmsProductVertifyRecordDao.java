package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.model.PmsProductVertifyRecord;

import java.util.List;

public interface PmsProductVertifyRecordDao {
    int insertList(@Param("list") List<PmsProductVertifyRecord> list);
}
