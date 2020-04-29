package top.tinn.service.PmsService.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.tinn.dto.PmsBrandParam;
import top.tinn.mapper.PmsBrandMapper;
import top.tinn.mapper.PmsProductMapper;
import top.tinn.model.PmsBrand;
import top.tinn.model.PmsBrandExample;
import top.tinn.model.PmsProduct;
import top.tinn.model.PmsProductExample;
import top.tinn.service.PmsService.PmsBrandService;

import java.util.List;

@Service
public class PmsBrandServiceImpl implements PmsBrandService {

    @Autowired
    private PmsBrandMapper brandMapper;

    @Autowired
    private PmsProductMapper productMapper;

    @Override
    public List<PmsBrand> listAllBrand() {
        return brandMapper.selectByExample(new PmsBrandExample());
    }

    @Override
    public int createBrand(PmsBrandParam pmsBrandParam) {
        PmsBrand pmsBrand=new PmsBrand();
        BeanUtils.copyProperties(pmsBrandParam,pmsBrand);
        if (StringUtils.isEmpty(pmsBrand.getFirstLetter()))
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0,1));
        return brandMapper.insertSelective(pmsBrand);
    }

    @Override
    public int updateBrand(Long id, PmsBrandParam pmsBrandParam) {
        PmsBrand pmsBrand=new PmsBrand();
        BeanUtils.copyProperties(pmsBrandParam,pmsBrand);
        pmsBrand.setId(id);
        //如果创建时首字母为空，取名称的第一个为首字母
        if (StringUtils.isEmpty(pmsBrand.getFirstLetter())) {
            pmsBrand.setFirstLetter(pmsBrand.getName().substring(0, 1));
        }
        //更新品牌时要更新商品中的品牌名称
        PmsProduct pmsProduct=new PmsProduct();
        pmsProduct.setBrandName(pmsBrand.getName());
        PmsProductExample example=new PmsProductExample();
        example.createCriteria().andBrandIdEqualTo(id);
        productMapper.updateByExampleSelective(pmsProduct,example);
        //更新品牌数据
        return brandMapper.updateByPrimaryKeySelective(pmsBrand);
    }


    @Override
    public int deleteBrand(Long id) {
        return brandMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteBrand(List<Long> ids) {
        PmsBrandExample example=new PmsBrandExample();
        example.createCriteria().andIdIn(ids);
        return brandMapper.deleteByExample(example);
    }

    @Override
    public List<PmsBrand> listBrand(String keyword, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PmsBrandExample example=new PmsBrandExample();
        example.setOrderByClause("sort desc");
        PmsBrandExample.Criteria criteria=example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) criteria.andNameLike("%"+keyword+"%");
        return brandMapper.selectByExample(example);
    }


    @Override
    public PmsBrand getBrand(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        PmsBrand pmsBrand=new PmsBrand();
        pmsBrand.setShowStatus(showStatus);
        PmsBrandExample example=new PmsBrandExample();
        example.createCriteria().andIdIn(ids);
        return brandMapper.updateByExampleSelective(pmsBrand,example);
    }

    @Override
    public int updateFactoryStatus(List<Long> ids, Integer factoryStatus) {
        PmsBrand pmsBrand=new PmsBrand();
        pmsBrand.setFactoryStatus(factoryStatus);
        PmsBrandExample example=new PmsBrandExample();
        example.createCriteria().andIdIn(ids);
        return brandMapper.updateByExampleSelective(pmsBrand,example);
    }
}
