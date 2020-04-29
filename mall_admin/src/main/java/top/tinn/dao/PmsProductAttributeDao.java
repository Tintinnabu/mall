package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.dto.ProductAttrInfo;

import java.util.List;

public interface PmsProductAttributeDao {
    List<ProductAttrInfo> getProductAttrInfo(@Param("id") Long productCategoryId);
}
