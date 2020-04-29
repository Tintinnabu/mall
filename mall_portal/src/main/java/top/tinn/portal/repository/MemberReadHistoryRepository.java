package top.tinn.portal.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import top.tinn.portal.domain.MemberReadHistory;

import java.util.List;

/**
 * @Description MemberReadHistoryRepository
 * 会员商品浏览历史Repository
 * @Author Tinn
 * @Date 2020/4/11 15:39
 */
public interface MemberReadHistoryRepository extends
        MongoRepository<MemberReadHistory,String> {
    List<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId);
}
