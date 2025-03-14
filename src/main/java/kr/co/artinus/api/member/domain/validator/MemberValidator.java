package kr.co.artinus.api.member.domain.validator;

import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.member.domain.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {
    private final MemberRepository memberRepository;

    public Member validByPhone(String phone) {
        return memberRepository.findByPhone(phone)
                .orElseThrow(() -> new IllegalStateException("해당 번호로 가입한 회원이 존재하지 않습니다."));
    }
}
