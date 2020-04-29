package top.tinn.dto;

import lombok.Getter;
import lombok.Setter;
import top.tinn.model.PmsProductCategory;

import java.util.List;

/**
 * @Description PmsProductCategoryWithChildrenItem
 * @Author Tinn
 * @Date 2020/4/8 20:10
 */

public class PmsProductCategoryWithChildrenItem extends PmsProductCategory{
    @Getter
    @Setter
    private List<PmsProductCategory> children;
}
