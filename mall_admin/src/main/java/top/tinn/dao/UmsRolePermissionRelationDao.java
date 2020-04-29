package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.model.UmsPermission;
import top.tinn.model.UmsRolePermissionRelation;

import java.util.List;

public interface UmsRolePermissionRelationDao {
    /**
     * 通过角色Id获取权限列表
     * @param roleId
     * @return
     */
    List<UmsPermission> getPermissionList(@Param("roleId") Long roleId);

    /**
     * 批量插入角色和权限关系
     */
    int insertList(@Param("list") List<UmsRolePermissionRelation> list);
}
