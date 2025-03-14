package kr.co.artinus.api.subscribehistory.presentation;

import kr.co.artinus.api.subscribehistory.application.SubscribeHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/subscribe-history")
public class SubscribeHistoryController {
    private final SubscribeHistoryService subscribeHistoryService;

    @GetMapping
    public void getSubscribeHistory() {
        subscribeHistoryService.saveSubscribeHistory();
    }
}
