package kr.co.artinus.api.channel.domain.validator;

import kr.co.artinus.api.channel.domain.entity.Channel;
import kr.co.artinus.api.channel.domain.persistence.ChannelRepository;
import kr.co.artinus.global.common.message.FailHttpMessage;
import kr.co.artinus.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelValidator {
    private final ChannelRepository channelRepository;

    public Channel validById(Long id) {
        return channelRepository.findById(id)
                // 채널이 없으면 예외 발생
                .orElseThrow(() -> new BusinessException(FailHttpMessage.INVALID_INPUT_VALUE_CHANNEL));
    }
}
