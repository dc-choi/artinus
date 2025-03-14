package kr.co.artinus.api.subscribehistory.domain.persistence;

import kr.co.artinus.api.subscribehistory.domain.entity.SubscribeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscribeHistoryRepository extends JpaRepository<SubscribeHistory, Long> {}
