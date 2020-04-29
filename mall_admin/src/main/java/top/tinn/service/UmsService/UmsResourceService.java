package top.tinn.service.UmsService;

import top.tinn.model.UmsResource;

import java.util.List;

/**
 * 后台资源管理Service
 *
 */
public interface UmsResourceService {
    int create(UmsResource umsResource);

    int update(Long id, UmsResource umsResource);

    UmsResource getItem(Long id);

    int delete(Long id);

    List<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);

    List<UmsResource> listAll();
}
