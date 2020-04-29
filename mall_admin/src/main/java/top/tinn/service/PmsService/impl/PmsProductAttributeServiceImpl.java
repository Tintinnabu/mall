package top.tinn.service.PmsService.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tinn.dao.PmsProductAttributeDao;
import top.tinn.dto.PmsProductAttributeParam;
import top.tinn.dto.ProductAttrInfo;
import top.tinn.mapper.PmsProductAttributeCategoryMapper;
import top.tinn.mapper.PmsProductAttributeMapper;
import top.tinn.model.PmsProductAttribute;
import top.tinn.model.PmsProductAttributeCategory;
import top.tinn.model.PmsProductAttributeExample;
import top.tinn.service.PmsService.PmsProductAttributeService;

import java.util.List;

/**
 * @Description PmsProductAttributeServiceImpl
 * @Author Tinn
 * @Date 2020/4/8 17:04
 */
@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {
    @Autowired
    private PmsProductAttributeMapper productAttributeMapper;
    @Autowired
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;
    @Autowired
    private PmsProductAttributeDao productAttributeDao;
    @Override
    public List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andProductAttributeCategoryIdEqualTo(cid).andTypeEqualTo(type);
        return productAttributeMapper.selectByExample(example);
    }

    /**
     * 逻辑有点复杂 待再细看
     * @param pmsProductAttributeParam
     * @return
     */
    @Override
    public int create(PmsProductAttributeParam pmsProductAttributeParam) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(pmsProductAttributeParam,pmsProductAttribute);
        int count = productAttributeMapper.insertSelective(pmsProductAttribute);
        //新增商品属性以后 需要更新商品属性分类数量
        PmsProductAttributeCategory pmsProductAttributeCategory =  productAttributeCategoryMapper.selectByPrimaryKey(pmsProductAttribute.getProductAttributeCategoryId());
        if (pmsProductAttribute.getType() == 1) pmsProductAttributeCategory.setAttributeCount(pmsProductAttributeCategory.getAttributeCount() + 1);
        else pmsProductAttributeCategory.setParamCount(pmsProductAttributeCategory.getParamCount() + 1);
        productAttributeCategoryMapper.updateByPrimaryKeySelective(pmsProductAttributeCategory);
        return count;
    }

    @Override
    public int update(Long id, PmsProductAttributeParam productAttributeParam) {
        PmsProductAttribute productAttribute = new PmsProductAttribute();
        productAttribute.setId(id);
        BeanUtils.copyProperties(productAttributeParam,productAttribute);
        return productAttributeMapper.updateByPrimaryKeySelective(productAttribute);
    }

    @Override
    public PmsProductAttribute getItem(Long id) {
        return productAttributeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(List<Long> ids) {
        //获取分类
        PmsProductAttribute productAttribute = productAttributeMapper.selectByPrimaryKey(ids.get(0));
        Integer type = productAttribute.getType();
        PmsProductAttributeCategory productAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(productAttribute.getProductAttributeCategoryId());
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria().andIdIn(ids);
        int count = productAttributeMapper.deleteByExample(example);
        //删除完成后修改数量
        if(type==0){
            if(productAttributeCategory.getAttributeCount()>=count){
                productAttributeCategory.setAttributeCount(productAttributeCategory.getAttributeCount()-count);
            }else{
                productAttributeCategory.setAttributeCount(0);
            }
        }else if(type==1){
            if(productAttributeCategory.getParamCount()>=count){
                productAttributeCategory.setParamCount(productAttributeCategory.getParamCount()-count);
            }else{
                productAttributeCategory.setParamCount(0);
            }
        }
        productAttributeCategoryMapper.updateByPrimaryKey(productAttributeCategory);
        return count;

    }

    @Override
    public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
        return productAttributeDao.getProductAttrInfo(productCategoryId);
    }
}
