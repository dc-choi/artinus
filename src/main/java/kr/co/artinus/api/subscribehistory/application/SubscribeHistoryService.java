package kr.co.artinus.api.subscribehistory.application;

import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.member.domain.persistence.MemberRepository;
import kr.co.artinus.api.subscribehistory.domain.dto.FindAllSubscribeHistory;
import kr.co.artinus.api.subscribehistory.domain.persistence.SubscribeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeHistoryService {
    private final SubscribeHistoryRepository subscribeHistoryRepository;
    private final MemberRepository memberRepository;

    public PagedModel<FindAllSubscribeHistory> findAll(int page, Integer size, String phone) {
        Pageable pageable = PageRequest.of(page, size);
        // 회원이 존재하는지 확인
        Member member = memberRepository.findByPhone(phone)
                .orElseThrow(() -> new IllegalStateException("해당 번호로 가입한 회원이 존재하지 않습니다."));

        List<FindAllSubscribeHistory> list = subscribeHistoryRepository.findHistory(member.getId(), pageable)
                .stream()
                .map(
                tuple -> new FindAllSubscribeHistory(
                        tuple.get(0, Long.class),
                        tuple.get(1, LocalDateTime.class),
                        tuple.get(2, LocalDateTime.class)
                )).toList();

        long count = subscribeHistoryRepository.count();

        return new PagedModel<>(new PageImpl<>(list, pageable, count));
    }
}
