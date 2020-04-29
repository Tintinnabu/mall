package top.tinn.dto;

import lombok.Getter;
import lombok.Setter;
import top.tinn.model.UmsPermission;

import java.util.List;

/**
 * @Description UmsPermissionNode
 * @Author Tinn
 * @Date 2020/4/8 13:17
 */

@Getter
@Setter
public class UmsPermissionNode extends UmsPermission {
    private List<UmsPermissionNode> children;
}
