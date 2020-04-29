package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.model.PmsProductFullReduction;

import java.util.List;

public interface PmsProductFullReductionDao {
    int insertList(@Param("list") List<PmsProductFullReduction> productFullReductionList);

}
