package top.tinn.service.PmsService;


import org.springframework.transaction.annotation.Transactional;
import top.tinn.dto.PmsBrandParam;
import top.tinn.model.PmsBrand;

import java.util.List;

/**
 * 商品品牌Service
 * Created by macro on 2018/4/26.
 */
public interface PmsBrandService {
    List<PmsBrand> listAllBrand();

    int createBrand(PmsBrandParam pmsBrandParam);
    @Transactional
    int updateBrand(Long id, PmsBrandParam pmsBrandParam);

    int deleteBrand(Long id);

    int deleteBrand(List<Long> ids);

    List<PmsBrand> listBrand(String keyword, int pageNum, int pageSize);

    PmsBrand getBrand(Long id);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    int updateFactoryStatus(List<Long> ids, Integer factoryStatus);
}
