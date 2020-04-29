package top.tinn.service.CmsService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tinn.mapper.CmsPrefrenceAreaMapper;
import top.tinn.model.CmsPrefrenceArea;
import top.tinn.model.CmsPrefrenceAreaExample;
import top.tinn.service.CmsService.CmsPrefrenceAreaService;

import java.util.List;

/**
 * @Description CmsPrefrenceAreaServiceImpl
 * @Author Tinn
 * @Date 2020/4/9 10:02
 */
@Service
public class CmsPrefrenceAreaServiceImpl implements CmsPrefrenceAreaService {
    @Autowired
    private CmsPrefrenceAreaMapper prefrenceAreaMapper;

    @Override
    public List<CmsPrefrenceArea> listAll() {
        return prefrenceAreaMapper.selectByExample(new CmsPrefrenceAreaExample());
    }
}
