package kr.co.artinus.api.subscribe.domain.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.artinus.api.subscribe.domain.enumerated.SubscribeType;

public record SubscribeDto(
        @NotBlank(message = "phone is required")
        String phone,
        @NotNull(message = "channelId is required")
        @Min(value = 1, message = "channelId must be greater than 0")
        @Max(value = Long.MAX_VALUE, message = "channelId must be less than or equal to Long.MAX_VALUE")
        Long channelId,
        @NotNull(message = "subscribeType is required")
        SubscribeType subscribeType
) {}
