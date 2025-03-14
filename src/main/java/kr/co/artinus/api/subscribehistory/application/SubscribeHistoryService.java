package kr.co.artinus.api.subscribehistory.application;

import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.member.domain.persistence.MemberRepository;
import kr.co.artinus.api.member.domain.validator.MemberValidator;
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
    private final MemberValidator memberValidator;

    public PagedModel<FindAllSubscribeHistory> findAll(int page, Integer size, String phone) {
        // 회원이 존재하는지 확인
        Member member = memberValidator.validByPhone(phone);

        Pageable pageable = PageRequest.of(page, size);

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
