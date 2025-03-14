package kr.co.artinus.api.subscribe.application;

import kr.co.artinus.api.channel.domain.entity.Channel;
import kr.co.artinus.api.channel.domain.validator.ChannelValidator;
import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.member.domain.validator.MemberValidator;
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
    private final SubscribeHistoryRepository subscribeHistoryRepository;
    private final ExternalService externalService;

    private final MemberValidator memberValidator;
    private final ChannelValidator channelValidator;

    @Transactional
    public SubscribeDto subscribe(SubscribeDto subscribeDto) {
        // 회원이 존재하는지 확인
        Member member = memberValidator.validByPhone(subscribeDto.phone());

        // 채널이 존재하는지 확인하고 권한 체크
        Channel channel = channelValidator.validById(subscribeDto.channelId());
        channel.checkRole(HistoryType.SUBSCRIBE);

        Subscribe subscribe = subscribeRepository.findByMember(member)
                // 구독 정보가 존재하는 경우 구독 타입을 변경함.
                .map(existingSubscribe -> {
                    existingSubscribe.modifySubscribeType(subscribeDto.subscribeType(), HistoryType.SUBSCRIBE);

                    return existingSubscribe;
                })
                // 구독 정보가 존재하지 않는 경우 새로 생성함.
                .orElseGet(() -> Subscribe.builder()
                        .type(subscribeDto.subscribeType())
                        .member(member)
                        .build()
                );
        subscribeRepository.save(subscribe);

        // 구독 이력 저장
        SubscribeHistory subscribeHistory = SubscribeHistory.create(HistoryType.CANCEL, member, channel, subscribe);
        subscribeHistoryRepository.save(subscribeHistory);

        // random 값이 0이 나오는 경우 서버 에러로 간주
        List<GetRandomResponse> random = externalService.getRandom();
        if (random.isEmpty()) throw new BusinessException(FailHttpMessage.INTERNAL_SERVER_ERROR);
        if (random.getFirst().random() == 0) throw new BusinessException(FailHttpMessage.INTERNAL_SERVER_ERROR);

        return subscribeDto;
    }

    @Transactional
    public SubscribeDto unsubscribe(SubscribeDto subscribeDto) {
        // 회원이 존재하는지 확인
        Member member = memberValidator.validByPhone(subscribeDto.phone());

        // 채널이 존재하는지 확인하고 권한 체크
        Channel channel = channelValidator.validById(subscribeDto.channelId());
        channel.checkRole(HistoryType.CANCEL);

        // 구독 정보가 존재하지 않는 경우 예외 발생하고 존재하는 경우 구독 타입을 변경함.
        Subscribe subscribe = subscribeRepository.findByMember(member)
                .orElseThrow(() -> new BusinessException(FailHttpMessage.NOT_FOUND_SUBSCRIBE));
        subscribe.modifySubscribeType(subscribeDto.subscribeType(), HistoryType.CANCEL);
        subscribeRepository.save(subscribe);

        // 구독 이력 저장
        SubscribeHistory subscribeHistory = SubscribeHistory.create(HistoryType.CANCEL, member, channel, subscribe);
        subscribeHistoryRepository.save(subscribeHistory);

        // random 값이 0이 나오는 경우 서버 에러로 간주
        List<GetRandomResponse> random = externalService.getRandom();
        if (random.isEmpty()) throw new BusinessException(FailHttpMessage.INTERNAL_SERVER_ERROR);
        if (random.getFirst().random() == 0) throw new BusinessException(FailHttpMessage.INTERNAL_SERVER_ERROR);

        return subscribeDto;
    }
}
