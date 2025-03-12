package kr.co.artinus.api.subscribe.domain.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SubscribeType {
    NONE("NONE"),
    BASIC("BASIC"),
    PREMIUM("PREMIUM");

    private final String type;
}
