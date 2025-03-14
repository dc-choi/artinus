package kr.co.artinus.api.subscribehistory.domain.dto;

import java.time.LocalDateTime;

public record FindAllSubscribeHistory(
        Long channelId,
        LocalDateTime startDate,
        LocalDateTime endDate
) {}
