package top.tinn.service.UmsService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tinn.mapper.UmsResourceCategoryMapper;
import top.tinn.model.UmsResourceCategory;
import top.tinn.model.UmsResourceCategoryExample;
import top.tinn.service.UmsService.UmsResourceCategoryService;

import java.util.Date;
import java.util.List;

/**
 * @Description UmsResourceCategoryServiceImpl
 * @Author Tinn
 * @Date 2020/4/8 13:39
 */
@Service
public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {
    @Autowired
    private UmsResourceCategoryMapper umsResourceCategoryMapper;
    @Override
    public List<UmsResourceCategory> listAll() {
        UmsResourceCategoryExample example = new UmsResourceCategoryExample();
        example.setOrderByClause("sort desc");
        return umsResourceCategoryMapper.selectByExample(example);
    }

    @Override
    public int create(UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setCreateTime(new Date());
        return umsResourceCategoryMapper.insert(umsResourceCategory);
    }

    @Override
    public int update(Long id, UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setId(id);
        return umsResourceCategoryMapper.updateByPrimaryKey(umsResourceCategory);
    }

    @Override
    public int delete(Long id) {
        return umsResourceCategoryMapper.deleteByPrimaryKey(id);
    }
}
