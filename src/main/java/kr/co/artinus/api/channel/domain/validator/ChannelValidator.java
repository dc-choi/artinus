package kr.co.artinus.api.channel.domain.validator;

import kr.co.artinus.api.channel.domain.entity.Channel;
import kr.co.artinus.api.channel.domain.persistence.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChannelValidator {
    private final ChannelRepository channelRepository;

    public Channel validById(Long id) {
        return channelRepository.findById(id)
                // 채널이 없으면 예외 발생
                .orElseThrow(() -> new IllegalStateException("해당 채널이 존재하지 않습니다."));
    }
}
