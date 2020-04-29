package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.model.PmsProductCategoryAttributeRelation;

import java.util.List;

public interface PmsProductCategoryAttributeRelationDao {
    int insertList(@Param("list") List<PmsProductCategoryAttributeRelation> productCategoryAttributeRelationList);
}
