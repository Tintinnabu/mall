package top.tinn.service.OmsService.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tinn.mapper.OmsOrderReturnReasonMapper;
import top.tinn.model.OmsOrderReturnReason;
import top.tinn.model.OmsOrderReturnReasonExample;
import top.tinn.service.OmsService.OmsOrderReturnReasonService;

import java.util.Date;
import java.util.List;

/**
 * @Description OmsOrderReturnReasonServiceImpl
 * @Author Tinn
 * @Date 2020/4/9 14:10
 */
@Service
public class OmsOrderReturnReasonServiceImpl implements OmsOrderReturnReasonService {
    @Autowired
    private OmsOrderReturnReasonMapper orderReturnReasonMapper;
    @Override
    public int create(OmsOrderReturnReason returnReason) {
        returnReason.setCreateTime(new Date());
        return orderReturnReasonMapper.insertSelective(returnReason);
    }

    @Override
    public int update(Long id, OmsOrderReturnReason returnReason) {
        returnReason.setId(id);
        return orderReturnReasonMapper.updateByPrimaryKeySelective(returnReason);
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.createCriteria().andIdIn(ids);
        return orderReturnReasonMapper.deleteByExample(example);
    }

    @Override
    public List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.setOrderByClause("sort desc");
        return orderReturnReasonMapper.selectByExample(example);
    }

    @Override
    public int updateStatus(List<Long> ids, Integer status) {
        if(!status.equals(0)&&!status.equals(1)){
            return 0;
        }
        OmsOrderReturnReason orderReturnReason = new OmsOrderReturnReason();
        orderReturnReason.setStatus(status);
        OmsOrderReturnReasonExample example = new OmsOrderReturnReasonExample();
        example.createCriteria().andIdIn(ids);
        return orderReturnReasonMapper.updateByExampleSelective(orderReturnReason,example);
    }

    @Override
    public OmsOrderReturnReason getItem(Long id) {
        return orderReturnReasonMapper.selectByPrimaryKey(id);
    }
}
