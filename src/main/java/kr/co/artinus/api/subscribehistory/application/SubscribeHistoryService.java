package kr.co.artinus.api.subscribehistory.application;

import kr.co.artinus.api.subscribehistory.domain.persistence.SubscribeHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscribeHistoryService {
    private final SubscribeHistoryRepository subscribeHistoryRepository;

    public void saveSubscribeHistory() {
        // 구독 이력 저장 로직 구현
    }
}
