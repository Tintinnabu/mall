package top.tinn.service.OmsService.impl;

import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tinn.dao.OmsOrderReturnApplyDao;
import top.tinn.dto.OmsOrderReturnApplyResult;
import top.tinn.dto.OmsReturnApplyQueryParam;
import top.tinn.dto.OmsUpdateStatusParam;
import top.tinn.mapper.OmsOrderReturnApplyMapper;
import top.tinn.model.OmsOrderReturnApply;
import top.tinn.model.OmsOrderReturnApplyExample;
import top.tinn.service.OmsService.OmsOrderReturnApplyService;

import java.util.Date;
import java.util.List;

/**
 * @Description OmsOrderReturnApplyServiceImpl
 * @Author Tinn
 * @Date 2020/4/9 14:21
 */
@Service
public class OmsOrderReturnApplyServiceImpl implements OmsOrderReturnApplyService {
    @Autowired
    private OmsOrderReturnApplyDao orderReturnApplyDao;
    @Autowired
    private OmsOrderReturnApplyMapper orderReturnApplyMapper;
    @Override
    public List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        return orderReturnApplyDao.getList(queryParam);
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrderReturnApplyExample example = new OmsOrderReturnApplyExample();
        example.createCriteria().andIdIn(ids);
        return orderReturnApplyMapper.deleteByExample(example);
    }

    @Override
    public int updateStatus(Long id, OmsUpdateStatusParam statusParam) {
        Integer status = statusParam.getStatus();
        OmsOrderReturnApply returnApply = new OmsOrderReturnApply();

        returnApply.setId(id);
        returnApply.setStatus(status);
        if (status.equals(1)){
            //确认退货
            returnApply.setReturnAmount(statusParam.getReturnAmount());
            returnApply.setCompanyAddressId(statusParam.getCompanyAddressId());
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        }else if (status.equals(2)){
            //完成退货
            returnApply.setReceiveTime(new Date());
            returnApply.setReceiveMan(statusParam.getReceiveMan());
            returnApply.setReceiveNote(statusParam.getReceiveNote());
        }else if (status.equals(3)){
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        }else
            return 0;
        return orderReturnApplyMapper.updateByPrimaryKeySelective(returnApply);
    }

    @Override
    public OmsOrderReturnApplyResult getItem(Long id) {
        return orderReturnApplyDao.getDetail(id);
    }
}
