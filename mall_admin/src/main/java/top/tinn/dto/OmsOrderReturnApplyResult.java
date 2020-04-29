package top.tinn.dto;


import lombok.Getter;
import lombok.Setter;
import top.tinn.model.OmsCompanyAddress;
import top.tinn.model.OmsOrderReturnApply;

/**
 * @Description OmsOrderReturnApplyResult 申请信息封装
 * @Author Tinn
 * @Date 2020/4/9 14:20
 */
public class OmsOrderReturnApplyResult extends OmsOrderReturnApply {
    @Getter
    @Setter
    private OmsCompanyAddress companyAddress;
}
