package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.dto.PmsProductResult;
import top.tinn.model.CmsPrefrenceAreaProductRelation;
import top.tinn.model.CmsSubjectProductRelation;

public interface PmsProductDao {
    /**
     * 获取商品编辑信息
     */
    PmsProductResult getUpdateInfo(@Param("id") Long id);

    /*CmsSubjectProductRelation selectSubjectProductRelationByProductId(@Param("productId") Long productId);

    CmsPrefrenceAreaProductRelation selectPreferenceAreaProductRelationByProductId(@Param("productId") Long ProductId);*/
}
