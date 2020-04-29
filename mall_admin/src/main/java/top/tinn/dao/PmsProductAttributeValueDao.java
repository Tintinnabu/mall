package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.model.PmsProductAttributeValue;

import java.util.List;

public interface PmsProductAttributeValueDao {
    int insertList(@Param("list") List<PmsProductAttributeValue> productAttributeValueList);

}
