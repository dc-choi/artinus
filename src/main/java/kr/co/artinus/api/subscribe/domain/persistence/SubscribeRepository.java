package kr.co.artinus.api.subscribe.domain.persistence;

import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.subscribe.domain.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    Optional<Subscribe> findByMember(Member member);
}
