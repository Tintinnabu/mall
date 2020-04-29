package top.tinn.portal.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.portal.domain.CartProduct;
import top.tinn.portal.domain.PromotionProduct;

import java.util.List;

public interface PortalProductDao {
    CartProduct getCartProduct(@Param("id") Long id);
    List<PromotionProduct> getPromotionProductList(@Param("ids") List<Long> ids);
}
