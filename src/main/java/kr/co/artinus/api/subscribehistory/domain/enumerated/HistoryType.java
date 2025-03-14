package kr.co.artinus.api.subscribehistory.domain.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HistoryType {
    SUBSCRIBE("SUBSCRIBE"),
    CANCEL("CANCEL"),;

    private final String type;
}
