package kr.co.artinus.api.subscribe.application;

import kr.co.artinus.api.channel.domain.entity.Channel;
import kr.co.artinus.api.channel.domain.enumerated.ChannelRole;
import kr.co.artinus.api.channel.domain.persistence.ChannelRepository;
import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.member.domain.persistence.MemberRepository;
import kr.co.artinus.api.subscribe.domain.dto.SubscribeDto;
import kr.co.artinus.api.subscribe.domain.entity.Subscribe;
import kr.co.artinus.api.subscribe.domain.enumerated.SubscribeType;
import kr.co.artinus.api.subscribe.domain.persistence.SubscribeRepository;
import kr.co.artinus.api.subscribehistory.domain.persistence.SubscribeHistoryRepository;
import kr.co.artinus.global.exception.BusinessException;
import kr.co.artinus.infra.external.ExternalService;
import kr.co.artinus.infra.external.dto.GetRandomResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@SpringBootTest
class SubscribeServiceTest {
    @Autowired
    SubscribeService subscribeService;
    @Autowired
    ChannelRepository channelRepository;
    @Autowired
    SubscribeRepository subscribeRepository;
    @Autowired
    SubscribeHistoryRepository subscribeHistoryRepository;

    @MockitoBean
    ExternalService externalService;

    List<Channel> channels;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        channels = channelRepository.findAll();
    }

    @AfterEach
    void tearDown() {
        subscribeHistoryRepository.deleteAll();
        subscribeRepository.deleteAll();
    }

    @Test
    @Transactional
    @DisplayName("실패 케이스: 구독시 전화번호에 해당하는 회원이 없는 경우")
    void _1() {
        // given
        SubscribeDto subscribeDto = new SubscribeDto("1011112222", channels.get(1).getId(), SubscribeType.BASIC);

        // when & then
        assertThatThrownBy(() -> subscribeService.subscribe(subscribeDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("해당 번호로 가입한 회원이 존재하지 않습니다.");
    }

    @Test
    @Transactional
    @DisplayName("실패 케이스: 구독시 채널이 존재하지 않는 경우")
    void _2() {
        // given
        SubscribeDto subscribeDto = new SubscribeDto("1012345678", 999L, SubscribeType.BASIC);

        // when & then
        assertThatThrownBy(() -> subscribeService.subscribe(subscribeDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("해당 채널이 존재하지 않습니다.");
    }

    @Test
    @Transactional
    @DisplayName("실패 케이스: 구독시 채널은 존재하지만 채널의 권한이 없는 경우")
    void _3() {
        // given
        List<Channel> list = channels.stream()
                .filter(channel -> channel.getRole() == ChannelRole.CANCEL)
                .toList();

        SubscribeDto subscribeDto = new SubscribeDto("1012345678", list.getFirst().getId(), SubscribeType.BASIC);

        // when & then
        assertThatThrownBy(() -> subscribeService.subscribe(subscribeDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("채널의 권한이 없는 접근입니다.");
    }

    @Test
    @Transactional
    @DisplayName("실패 케이스: 구독시 서버 오류가 발생해서 구독이 실패하는 경우")
    void _4() {
        // given
        SubscribeDto subscribeDto = new SubscribeDto("1012345678", channels.get(1).getId(), SubscribeType.BASIC);

        List<GetRandomResponse> responses = List.of(new GetRandomResponse("", 0, 1, 0));
        when(externalService.getRandom()).thenReturn(responses);

        // when & then
        assertThatThrownBy(() -> subscribeService.subscribe(subscribeDto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("서버 에러 입니다.");
    }

    @Test
    @Transactional
    @DisplayName("실패 케이스: 구독 취소 시 전화번호에 해당하는 회원이 없는 경우")
    void _5() {
        // given
        SubscribeDto subscribeDto = new SubscribeDto("1011112222", channels.get(1).getId(), SubscribeType.BASIC);

        // when & then
        assertThatThrownBy(() -> subscribeService.unsubscribe(subscribeDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("해당 번호로 가입한 회원이 존재하지 않습니다.");
    }

    @Test
    @Transactional
    @DisplayName("실패 케이스: 구독 취소 시 채널이 존재하지 않는 경우")
    void _6() {
        // given
        SubscribeDto subscribeDto = new SubscribeDto("1012345678", 999L, SubscribeType.BASIC);
        // when & then
        assertThatThrownBy(() -> subscribeService.unsubscribe(subscribeDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("해당 채널이 존재하지 않습니다.");
    }

    @Test
    @Transactional
    @DisplayName("실패 케이스: 구독 취소 시 채널은 존재하지만 채널의 권한이 없는 경우")
    void _7() {
        // given
        List<Channel> list = channels.stream()
                .filter(channel -> channel.getRole() == ChannelRole.SUBSCRIBE)
                .toList();

        SubscribeDto subscribeDto = new SubscribeDto("1012345678", list.getFirst().getId(), SubscribeType.BASIC);

        // when & then
        assertThatThrownBy(() -> subscribeService.unsubscribe(subscribeDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("채널의 권한이 없는 접근입니다.");
    }

    @Test
    @Transactional
    @DisplayName("실패 케이스: 구독 취소 시 구독 정보가 없는 경우")
    void _8() {
        // given
        SubscribeDto subscribeDto = new SubscribeDto("1012345678", channels.get(1).getId(), SubscribeType.BASIC);

        // when & then
        assertThatThrownBy(() -> subscribeService.unsubscribe(subscribeDto))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("구독 정보가 존재하지 않습니다.");
    }

    @Test
    @Transactional
    @DisplayName("실패 케이스: 구독 취소 시 서버 오류가 발생해서 구독이 실패하는 경우")
    void _9() {
        // given
        Member member = memberRepository.findByPhone("1012345678")
                .orElseThrow(() -> new IllegalStateException("해당 번호로 가입한 회원이 존재하지 않습니다."));

        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.PREMIUM)
                .member(member)
                .build();
        subscribeRepository.saveAndFlush(subscribe);

        SubscribeDto subscribeDto = new SubscribeDto("1012345678", channels.get(1).getId(), SubscribeType.BASIC);

        List<GetRandomResponse> responses = List.of(new GetRandomResponse("", 0, 1, 0));
        when(externalService.getRandom()).thenReturn(responses);

        // when & then
        assertThatThrownBy(() -> subscribeService.unsubscribe(subscribeDto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("서버 에러 입니다.");
    }
}