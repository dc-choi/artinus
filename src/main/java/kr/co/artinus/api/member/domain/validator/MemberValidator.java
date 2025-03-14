package kr.co.artinus.api.member.domain.validator;

import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.member.domain.persistence.MemberRepository;
import kr.co.artinus.global.common.message.FailHttpMessage;
import kr.co.artinus.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {
    private final MemberRepository memberRepository;

    public Member validByPhone(String phone) {
        return memberRepository.findByPhone(phone)
                .orElseThrow(() -> new BusinessException(FailHttpMessage.NOT_FOUND_MEMBER));
    }
}
