package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.model.UmsAdminPermissionRelation;

import java.util.List;

public interface UmsAdminPermissionRelationDao {
    int insertList(@Param("list") List<UmsAdminPermissionRelation> List) ;
}
