package top.tinn.dao;

import org.apache.ibatis.annotations.Param;
import top.tinn.model.PmsProductLadder;

import java.util.List;

/**
 * @Description PmsProductLadderDao
 * @Author Tinn
 * @Date 2020/4/8 21:20
 */
public interface PmsProductLadderDao {
    int insertList(@Param("list") List<PmsProductLadder> productLadderList);
}
