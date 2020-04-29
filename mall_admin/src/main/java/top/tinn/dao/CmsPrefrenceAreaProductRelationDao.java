package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.model.CmsPrefrenceAreaProductRelation;

import java.util.List;

public interface CmsPrefrenceAreaProductRelationDao {
    int insertList(@Param("list") List<CmsPrefrenceAreaProductRelation> prefrenceAreaProductRelationList);

}
