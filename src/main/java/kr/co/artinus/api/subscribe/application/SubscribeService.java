package kr.co.artinus.api.subscribe.application;

import kr.co.artinus.api.channel.domain.entity.Channel;
import kr.co.artinus.api.channel.domain.enumerated.ChannelRole;
import kr.co.artinus.api.channel.domain.persistence.ChannelRepository;
import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.member.domain.persistence.MemberRepository;
import kr.co.artinus.api.subscribe.domain.dto.SubscribeDto;
import kr.co.artinus.api.subscribe.domain.entity.Subscribe;
import kr.co.artinus.api.subscribe.domain.persistence.SubscribeRepository;
import kr.co.artinus.api.subscribehistory.domain.entity.SubscribeHistory;
import kr.co.artinus.api.subscribehistory.domain.enumerated.HistoryType;
import kr.co.artinus.api.subscribehistory.domain.persistence.SubscribeHistoryRepository;
import kr.co.artinus.global.common.message.FailHttpMessage;
import kr.co.artinus.global.exception.BusinessException;
import kr.co.artinus.infra.external.ExternalService;
import kr.co.artinus.infra.external.dto.GetRandomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {
    private final SubscribeRepository subscribeRepository;
    private final MemberRepository memberRepository;
    private final ChannelRepository channelRepository;
    private final SubscribeHistoryRepository subscribeHistoryRepository;
    private final ExternalService externalService;

    @Transactional
    public SubscribeDto subscribe(SubscribeDto subscribeDto) {
        // 회원이 존재하는지 확인
        Member member = memberRepository.findByPhone(subscribeDto.phone())
                .orElseThrow(() -> new IllegalArgumentException("해당 번호로 가입한 회원이 존재하지 않습니다."));

        Channel channel = channelRepository.findById(subscribeDto.channelId())
                .map(existingChannel -> {
                    // 채널 권한이 EVERY, SUBSCRIBE면 구독 가능
                    if (existingChannel.getRole() != ChannelRole.EVERY && existingChannel.getRole() != ChannelRole.SUBSCRIBE) {
                        throw new BusinessException(FailHttpMessage.FORBIDDEN);
                    }

                    return existingChannel;
                })
                // 채널이 없으면 예외 발생
                .orElseThrow(() -> new IllegalArgumentException("해당 채널이 존재하지 않습니다."));

        Subscribe subscribe = subscribeRepository.findByMember(member)
                // 구독 정보가 존재하는 경우 구독 타입을 변경함.
                .map(existingSubscribe -> {
                    existingSubscribe.modifySubscribeType(subscribeDto.subscribeType());

                    return existingSubscribe;
                })
                // 구독 정보가 존재하지 않는 경우 새로 생성함.
                .orElseGet(() -> subscribeRepository.save(
                        Subscribe.builder()
                                .type(subscribeDto.subscribeType())
                                .member(member)
                                .build()
                ));

        SubscribeHistory subscribeHistory = SubscribeHistory.builder()
                .type(HistoryType.SUBSCRIBE)
                .changedAt(subscribe.getCreatedAt())
                .member(member)
                .channel(channel)
                .subscribe(subscribe)
                .build();

        subscribeHistory.addChannel(channel);
        subscribeHistory.addMember(member);
        subscribeHistory.addSubscribe(subscribe);

        // 구독 이력 저장
        subscribeHistoryRepository.save(subscribeHistory);

        List<GetRandomResponse> random = externalService.getRandom();
        if (random.isEmpty()) throw new BusinessException(FailHttpMessage.INTERNAL_SERVER_ERROR);
        if (random.getFirst().random() == 0) throw new BusinessException(FailHttpMessage.INTERNAL_SERVER_ERROR);

        return subscribeDto;
    }
}
