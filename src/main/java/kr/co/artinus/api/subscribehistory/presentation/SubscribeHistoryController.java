package kr.co.artinus.api.subscribehistory.presentation;

import kr.co.artinus.api.subscribehistory.application.SubscribeHistoryService;
import kr.co.artinus.api.subscribehistory.domain.dto.FindAllSubscribeHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/subscribe-history")
public class SubscribeHistoryController {
    private final SubscribeHistoryService subscribeHistoryService;

    @GetMapping
    public ResponseEntity<PagedModel<FindAllSubscribeHistory>> findAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "20") int size,
            @RequestParam(name = "phone", required = true) String phone
    ) {
        PagedModel<FindAllSubscribeHistory> findAllSubscribeHistories = subscribeHistoryService.findAll(page, size, phone);

        return ResponseEntity.ok().body(findAllSubscribeHistories);
    }
}
