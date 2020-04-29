package top.tinn.service.UmsService.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.tinn.mapper.UmsResourceMapper;
import top.tinn.model.UmsResource;
import top.tinn.model.UmsResourceExample;
import top.tinn.service.UmsService.UmsResourceService;

import java.util.Date;
import java.util.List;

@Service
public class UmsResourceServiceImpl implements UmsResourceService {
    @Autowired
    private UmsResourceMapper resourceMapper;


    @Override
    public int create(UmsResource umsResource) {
        umsResource.setCreateTime(new Date());
        return resourceMapper.insert(umsResource);
    }

    @Override
    public int update(Long id, UmsResource umsResource) {
        umsResource.setId(id);
        return resourceMapper.updateByPrimaryKeySelective(umsResource);
    }

    @Override
    public UmsResource getItem(Long id) {
        return resourceMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(Long id) {
        return resourceMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        UmsResourceExample example=new UmsResourceExample();
        UmsResourceExample.Criteria criteria=example.createCriteria();
        if (categoryId!=null) criteria.andCategoryIdEqualTo(categoryId);
        if (!StringUtils.isEmpty(nameKeyword)) criteria.andNameLike("%"+nameKeyword+"%");
        if (!StringUtils.isEmpty(urlKeyword)) criteria.andUrlLike("%"+urlKeyword+"%");
        return resourceMapper.selectByExample(example);
    }

    @Override
    public List<UmsResource> listAll() {
        return resourceMapper.selectByExample(new UmsResourceExample());
    }
}
