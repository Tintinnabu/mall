package top.tinn.service.UmsService.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tinn.dto.UmsPermissionNode;
import top.tinn.mapper.UmsPermissionMapper;
import top.tinn.model.UmsPermission;
import top.tinn.model.UmsPermissionExample;
import top.tinn.service.UmsService.UmsPermissionService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description UmsPermissionServiceImpl
 * @Author Tinn
 * @Date 2020/4/8 13:32
 */

@Service
public class UmsPermissionServiceImpl implements UmsPermissionService {
    @Autowired
    private UmsPermissionMapper permissionMapper;
    @Override
    public int create(UmsPermission permission) {
        permission.setCreateTime(new Date());
        permission.setStatus(1);
        permission.setSort(0);
        return permissionMapper.insert(permission);
    }

    @Override
    public int update(Long id, UmsPermission permission) {
        permission.setId(id);
        return permissionMapper.updateByPrimaryKeySelective(permission);
    }

    @Override
    public int delete(List<Long> ids) {
        UmsPermissionExample example=new UmsPermissionExample();
        example.createCriteria().andIdIn(ids);
        return permissionMapper.deleteByExample(example);
    }

    @Override
    public List<UmsPermissionNode> treeList() {
        List<UmsPermission> permissionList=list();
        List<UmsPermissionNode> result=permissionList.stream()
                .filter(permission->permission.getPid().equals(0L))
                .map(permission->convert(permission,permissionList))
                .collect(Collectors.toList());
        return result;
    }

    private UmsPermissionNode convert(UmsPermission permission, List<UmsPermission> permissionList) {
        UmsPermissionNode node=new UmsPermissionNode();
        BeanUtils.copyProperties(permission,node);
        List<UmsPermissionNode> children=permissionList.stream()
                .filter(subPermission->subPermission.getPid().equals(permission.getId()))
                .map(subPermission->convert(subPermission,permissionList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    @Override
    public List<UmsPermission> list() {
        return permissionMapper.selectByExample(new UmsPermissionExample());
    }
}
