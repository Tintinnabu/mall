package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.model.UmsMenu;
import top.tinn.model.UmsResource;

import java.util.List;

public interface UmsRoleDao {

    List<UmsMenu> getMenuList(@Param("adminId") Long adminId);

    List<UmsMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

    List<UmsResource> getResourceListByRoleId(@Param("roleId") Long roleId);
}
