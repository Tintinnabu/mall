package top.tinn.service.PmsService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import top.tinn.dao.PmsSkuStockDao;
import top.tinn.mapper.PmsSkuStockMapper;
import top.tinn.model.PmsSkuStock;
import top.tinn.model.PmsSkuStockExample;
import top.tinn.service.PmsService.PmsSkuStockService;

import java.util.List;

/**
 * @Description PmsSkuStockServiceImpl
 * @Author Tinn
 * @Date 2020/4/8 20:52
 */
@Service
public class PmsSkuStockServiceImpl implements PmsSkuStockService {
    @Autowired
    private PmsSkuStockMapper skuStockMapper;
    @Autowired
    private PmsSkuStockDao skuStockDao;

    @Override
    public List<PmsSkuStock> getList(Long pid, String keyword) {
        PmsSkuStockExample example = new PmsSkuStockExample();
        PmsSkuStockExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) criteria.andSkuCodeLike("%"+keyword+"%");
        return skuStockMapper.selectByExample(example);
    }

    @Override
    public int update(Long pid, List<PmsSkuStock> skuStockList) {
        return skuStockDao.replaceList(skuStockList);
    }
}
