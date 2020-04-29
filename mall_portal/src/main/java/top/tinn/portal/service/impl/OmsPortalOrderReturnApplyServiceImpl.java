package top.tinn.portal.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tinn.mapper.OmsOrderReturnApplyMapper;
import top.tinn.model.OmsOrderReturnApply;
import top.tinn.portal.domain.OmsOrderReturnApplyParam;
import top.tinn.portal.service.OmsPortalOrderReturnApplyService;

import java.util.Date;

/**
 * @Description OmsPortalOrderReturnApplyServiceImpl
 * @Author Tinn
 * @Date 2020/4/10 20:44
 */

@Service
public class OmsPortalOrderReturnApplyServiceImpl implements OmsPortalOrderReturnApplyService {
    @Autowired
    private OmsOrderReturnApplyMapper returnApplyMapper;
    @Override
    public int create(OmsOrderReturnApplyParam returnApply) {
        OmsOrderReturnApply realApply = new OmsOrderReturnApply();
        BeanUtils.copyProperties(returnApply,realApply);
        realApply.setCreateTime(new Date());
        realApply.setStatus(0);
        return returnApplyMapper.insert(realApply);
    }
}
