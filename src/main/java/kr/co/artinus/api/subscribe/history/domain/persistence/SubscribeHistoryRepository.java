package kr.co.artinus.api.subscribe.history.domain.persistence;

import kr.co.artinus.api.subscribe.history.domain.entity.SubscribeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeHistoryRepository extends JpaRepository<SubscribeHistory, Long> {}
