package kr.co.artinus.api.subscribe.presentation;

import jakarta.validation.Valid;
import kr.co.artinus.api.subscribe.application.SubscribeService;
import kr.co.artinus.api.subscribe.domain.dto.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/subscribes")
public class SubscribeController {
    private final SubscribeService subscribeService;

    @PostMapping
    public ResponseEntity<SubscribeDto> subscribe(@RequestBody @Valid SubscribeDto subscribeDto) {
        SubscribeDto subscribe = subscribeService.subscribe(subscribeDto);

        return ResponseEntity.ok().body(subscribe);
    }
}
