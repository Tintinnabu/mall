package top.tinn.portal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.tinn.portal.domain.MemberReadHistory;
import top.tinn.portal.repository.MemberReadHistoryRepository;
import top.tinn.portal.service.MemberReadHistoryService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description MemberReadHistoryServiceImpl
 * @Author Tinn
 * @Date 2020/4/11 15:42
 */
@Service
public class MemberReadHistoryServiceImpl
        implements MemberReadHistoryService {
    @Autowired
    private MemberReadHistoryRepository memberReadHistoryRepository;
    @Override
    public int create(MemberReadHistory memberReadHistory) {
        memberReadHistory.setId(null);
        memberReadHistory.setCreateTime(new Date());
        memberReadHistoryRepository.save(memberReadHistory);
        return 1;
    }

    @Override
    public int delete(List<String> ids) {
        List<MemberReadHistory> deleteList = ids.stream().map(id->{
            MemberReadHistory memberReadHistory = new MemberReadHistory();
            memberReadHistory.setId(id);
            return memberReadHistory;
        }).collect(Collectors.toList());
        memberReadHistoryRepository.deleteAll(deleteList);
        return deleteList.size();
    }

    @Override
    public List<MemberReadHistory> list(Long memberId) {
        return memberReadHistoryRepository
                .findByMemberIdOrderByCreateTimeDesc(memberId);
    }
}
