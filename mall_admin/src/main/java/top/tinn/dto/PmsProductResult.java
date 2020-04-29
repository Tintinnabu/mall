package top.tinn.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description PmsProductResult 查询单个产品进行修改时返回的结果
 * @Author Tinn
 * @Date 2020/4/8 20:48
 */
public class PmsProductResult extends PmsProductParam {
    //商品所选分类的父id
    @Setter
    @Getter
    private Long cateParentId;
}
