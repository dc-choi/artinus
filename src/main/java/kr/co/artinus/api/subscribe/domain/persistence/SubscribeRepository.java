package kr.co.artinus.api.subscribe.domain.persistence;

import kr.co.artinus.api.subscribe.domain.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {}
