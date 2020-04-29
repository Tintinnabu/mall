package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.model.PmsMemberPrice;

import java.util.List;

public interface PmsMemberPriceDao {
    int insertList(@Param("list") List<PmsMemberPrice> memberPriceList);
}
