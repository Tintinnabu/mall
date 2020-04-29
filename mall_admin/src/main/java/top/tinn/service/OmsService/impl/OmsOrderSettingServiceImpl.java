package top.tinn.service.OmsService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tinn.mapper.OmsOrderSettingMapper;
import top.tinn.model.OmsOrderSetting;
import top.tinn.service.OmsService.OmsOrderSettingService;

/**
 * @Description OmsOrderSettingServiceImpl
 * @Author Tinn
 * @Date 2020/4/9 14:08
 */
@Service
public class OmsOrderSettingServiceImpl implements OmsOrderSettingService {
    @Autowired
    private OmsOrderSettingMapper orderSettingMapper;
    @Override
    public OmsOrderSetting getItem(Long id) {
        return orderSettingMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(Long id, OmsOrderSetting orderSetting) {
        orderSetting.setId(id);
        return orderSettingMapper.updateByPrimaryKey(orderSetting);
    }
}
