package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.model.CmsSubjectProductRelation;

import java.util.List;

public interface CmsSubjectProductRelationDao {
    int insertList(@Param("list") List<CmsSubjectProductRelation> subjectProductRelationList);

}
