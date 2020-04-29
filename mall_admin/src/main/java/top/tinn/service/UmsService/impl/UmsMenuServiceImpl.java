package top.tinn.service.UmsService.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tinn.dto.UmsMenuNode;
import top.tinn.mapper.UmsMenuMapper;
import top.tinn.model.UmsMenu;
import top.tinn.model.UmsMenuExample;
import top.tinn.service.UmsService.UmsMenuService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description UmsMenuServiceImpl
 * @Author Tinn
 * @Date 2020/4/8 11:31
 */

@Service
public class UmsMenuServiceImpl implements UmsMenuService {
    @Autowired
    private UmsMenuMapper menuMapper;

    @Override
    public int create(UmsMenu umsMenu) {
        umsMenu.setCreateTime(new Date());
        updateLevel(umsMenu);
        return menuMapper.insert(umsMenu);
    }

    /**
     * 修改菜单层级
     */
    private void updateLevel(UmsMenu umsMenu) {
        //没有父菜单时为一级菜单
        if (umsMenu.getParentId()==0) umsMenu.setLevel(0);
        else {
            UmsMenu parentMenu=menuMapper.selectByPrimaryKey(umsMenu.getParentId());
            if (parentMenu!=null) umsMenu.setLevel(parentMenu.getLevel()+1);
            else umsMenu.setLevel(0);
        }
    }

    @Override
    public int update(Long id, UmsMenu umsMenu) {
        umsMenu.setId(id);
        updateLevel(umsMenu);
        return menuMapper.updateByPrimaryKeySelective(umsMenu);
    }

    @Override
    public UmsMenu getItem(Long id) {
        return menuMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        UmsMenuExample example=new UmsMenuExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andParentIdEqualTo(parentId);
        return menuMapper.selectByExample(example);
    }

    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenu> menuList=menuMapper.selectByExample(new UmsMenuExample());
        List<UmsMenuNode> result=menuList.stream()
                .filter(menu->menu.getParentId().equals(0L))
                .map(menu->convertMenuNode(menu,menuList)).collect(Collectors.toList());
        return result;
    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     * 使用递归实现
     */
    private UmsMenuNode convertMenuNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode node=new UmsMenuNode();
        BeanUtils.copyProperties(menu,node);
        List<UmsMenuNode> children=menuList.stream()
                .filter(subMenu->subMenu.getParentId().equals(menu.getId()))
                .map(subMenu->convertMenuNode(subMenu,menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        UmsMenu umsMenu=new UmsMenu();
        umsMenu.setId(id);
        umsMenu.setHidden(hidden);
        return menuMapper.updateByPrimaryKeySelective(umsMenu);
    }
}
