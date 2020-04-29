package top.tinn.dto;

import lombok.Getter;
import lombok.Setter;
import top.tinn.model.PmsProductAttribute;
import top.tinn.model.PmsProductAttributeCategory;

import java.util.List;

/**
 * @Description PmsProductAttributeCategoryItem 包含有分类下属性的dto
 * @Author Tinn
 * @Date 2020/4/8 16:49
 */

public class PmsProductAttributeCategoryItem extends PmsProductAttributeCategory {
    @Getter
    @Setter
    private List<PmsProductAttribute> productAttributeList;//曾名productAttributeList
}
