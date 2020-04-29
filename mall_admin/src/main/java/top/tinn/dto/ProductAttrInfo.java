package top.tinn.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description ProductAttrInfo 商品分类对应属性信息
 * @Author Tinn
 * @Date 2020/4/8 19:58
 */
@Getter
@Setter
public class ProductAttrInfo {
    private Long attributeId;
    private Long attributeCategoryId;
}
