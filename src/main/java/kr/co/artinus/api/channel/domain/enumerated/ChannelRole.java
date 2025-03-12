package kr.co.artinus.api.channel.domain.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChannelRole {
    EVERY("EVERY"),
    SUBSCRIBE("SUBSCRIBE"),
    CANCEL("CANCEL");

    private final String type;
}
