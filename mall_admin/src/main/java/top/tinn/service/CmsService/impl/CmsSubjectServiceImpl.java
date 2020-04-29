package top.tinn.service.CmsService.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.tinn.mapper.CmsSubjectMapper;
import top.tinn.model.CmsSubject;
import top.tinn.model.CmsSubjectExample;
import top.tinn.service.CmsService.CmsSubjectService;

import java.util.List;

/**
 * @Description CmsSubjectServiceImpl
 * @Author Tinn
 * @Date 2020/4/9 10:04
 */
@Service
public class CmsSubjectServiceImpl implements CmsSubjectService {
    @Autowired
    private CmsSubjectMapper subjectMapper;
    @Override
    public List<CmsSubject> listAll() {
        return subjectMapper.selectByExample(new CmsSubjectExample());
    }

    @Override
    public List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        CmsSubjectExample example = new CmsSubjectExample();
        CmsSubjectExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) criteria.andTitleLike("%"+keyword+"%");
        return subjectMapper.selectByExample(example);
    }
}
