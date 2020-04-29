package top.tinn.portal.domain;

import lombok.Getter;
import lombok.Setter;
import top.tinn.model.PmsProduct;
import top.tinn.model.PmsProductAttribute;
import top.tinn.model.PmsSkuStock;

import java.util.List;

/**
 * @Description CartProduct 购物车中选择规格的商品信息
 * @Author Tinn
 * @Date 2020/4/10 16:55
 */
@Getter
@Setter
public class CartProduct extends PmsProduct {


    private List<PmsProductAttribute> productAttributeList;
    private List<PmsSkuStock> skuStockList;
}
