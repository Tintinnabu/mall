package top.tinn.dto;

import lombok.Getter;
import lombok.Setter;
import top.tinn.model.UmsMenu;

import java.util.List;

/**
 * @Author Tinn
 * @Date 2020/4/8 11:29
 */
@Getter
@Setter
public class UmsMenuNode extends UmsMenu {
    private List<UmsMenuNode> children;
}
