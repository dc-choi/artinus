package kr.co.artinus.api.channel.domain.entity;

import kr.co.artinus.api.channel.domain.enumerated.ChannelRole;
import kr.co.artinus.api.subscribehistory.domain.enumerated.HistoryType;
import kr.co.artinus.global.common.message.FailHttpMessage;
import kr.co.artinus.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ChannelTest {
    @Test
    @DisplayName("채널 권한 - 구독시 모든 권한")
    void _1() {
        // given
        Channel channel = Channel.builder()
                .role(ChannelRole.EVERY)
                .build();

        // when
        channel.checkRole(HistoryType.SUBSCRIBE);

        // then
        assertThat(ChannelRole.EVERY).isEqualTo(channel.getRole());
    }

    @Test
    @DisplayName("채널 권한 - 구독시 구독 권한")
    void _2() {
        // given
        Channel channel = Channel.builder()
                .role(ChannelRole.SUBSCRIBE)
                .build();

        // when
        channel.checkRole(HistoryType.SUBSCRIBE);

        // then
        assertThat(ChannelRole.SUBSCRIBE).isEqualTo(channel.getRole());
    }

    @Test
    @DisplayName("실패 케이스: 채널 권한 - 구독시 구독 취소 권한")
    void _3() {
        // given
        Channel channel = Channel.builder()
                .role(ChannelRole.CANCEL)
                .build();

        // when & then
        assertThatThrownBy(() -> channel.checkRole(HistoryType.SUBSCRIBE))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(FailHttpMessage.FORBIDDEN_CHANNEL.getMessage());
    }

    @Test
    @DisplayName("채널 권한 - 구독 취소시 모든 권한")
    void _4() {
        // given
        Channel channel = Channel.builder()
                .role(ChannelRole.EVERY)
                .build();

        // when
        channel.checkRole(HistoryType.CANCEL);

        // then
        assertThat(ChannelRole.EVERY).isEqualTo(channel.getRole());
    }

    @Test
    @DisplayName("채널 권한 - 구독 취소시 구독 취소 권한")
    void _5() {
        // given
        Channel channel = Channel.builder()
                .role(ChannelRole.CANCEL)
                .build();

        // when
        channel.checkRole(HistoryType.CANCEL);

        // then
        assertThat(ChannelRole.CANCEL).isEqualTo(channel.getRole());
    }

    @Test
    @DisplayName("실패 케이스: 채널 권한 - 구독 취소시 구독 권한")
    void _6() {
        // given
        Channel channel = Channel.builder()
                .role(ChannelRole.SUBSCRIBE)
                .build();

        // when & then
        assertThatThrownBy(() -> channel.checkRole(HistoryType.CANCEL))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(FailHttpMessage.FORBIDDEN_CHANNEL.getMessage());
    }
}