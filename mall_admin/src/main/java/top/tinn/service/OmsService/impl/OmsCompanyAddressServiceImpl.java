package top.tinn.service.OmsService.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tinn.mapper.OmsCompanyAddressMapper;
import top.tinn.model.OmsCompanyAddress;
import top.tinn.model.OmsCompanyAddressExample;
import top.tinn.service.OmsService.OmsCompanyAddressService;

import java.util.List;

/**
 * @Description OmsCompanyAddressServiceImpl
 * @Author Tinn
 * @Date 2020/4/9 14:07
 */
@Service
public class OmsCompanyAddressServiceImpl implements OmsCompanyAddressService {
    @Autowired
    private OmsCompanyAddressMapper companyAddressMapper;
    @Override
    public List<OmsCompanyAddress> list() {
        return companyAddressMapper.selectByExample(new OmsCompanyAddressExample());
    }
}
