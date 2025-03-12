package kr.co.artinus.api.member.domain.persistence;

import kr.co.artinus.api.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByPhone(String phone);
}
