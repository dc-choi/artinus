package kr.co.artinus.api.subscribe.domain.entity;

import kr.co.artinus.api.subscribe.domain.enumerated.SubscribeType;
import kr.co.artinus.api.subscribehistory.domain.enumerated.HistoryType;
import kr.co.artinus.global.common.message.FailHttpMessage;
import kr.co.artinus.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubscribeTest {
    @Test
    @DisplayName("구독시 구독 상태를 구독 안함에서 일반 구독으로 변경")
    void _1() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.NONE)
                .build();

        // when
        subscribe.modifySubscribeType(SubscribeType.BASIC, HistoryType.SUBSCRIBE);

        // then
        assertThat(SubscribeType.BASIC).isEqualTo(subscribe.getType());
    }

    @Test
    @DisplayName("구독시 구독 상태를 구독 안함에서 프리미엄 구독으로 변경")
    void _2() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.NONE)
                .build();

        // when
        subscribe.modifySubscribeType(SubscribeType.PREMIUM, HistoryType.SUBSCRIBE);

        // then
        assertThat(SubscribeType.PREMIUM).isEqualTo(subscribe.getType());
    }

    @Test
    @DisplayName("구독시 구독 상태를 일반 구독에서 프리미엄 구독으로 변경")
    void _3() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.BASIC)
                .build();

        // when
        subscribe.modifySubscribeType(SubscribeType.PREMIUM, HistoryType.SUBSCRIBE);

        // then
        assertThat(SubscribeType.PREMIUM).isEqualTo(subscribe.getType());
    }

    @Test
    @DisplayName("구독시 구독 상태를 프리미엄 구독에서 프리미엄 구독으로 변경")
    void _4() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.PREMIUM)
                .build();

        // when
        subscribe.modifySubscribeType(SubscribeType.PREMIUM, HistoryType.SUBSCRIBE);

        // then
        assertThat(SubscribeType.PREMIUM).isEqualTo(subscribe.getType());
    }

    @Test
    @DisplayName("실패 케이스: 구독시 구독 상태를 구독 안함에서 구독 안함으로 변경")
    void _5() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.NONE)
                .build();

        // when & then
        assertThatThrownBy(() -> subscribe.modifySubscribeType(SubscribeType.NONE, HistoryType.SUBSCRIBE))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(FailHttpMessage.BAD_REQUEST_SUBSCRIBE.getMessage());
    }

    @Test
    @DisplayName("실패 케이스: 구독시 구독 상태를 일반 구독에서 구독 안함으로 변경")
    void _6() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.BASIC)
                .build();

        // when & then
        assertThatThrownBy(() -> subscribe.modifySubscribeType(SubscribeType.NONE, HistoryType.SUBSCRIBE))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(FailHttpMessage.BAD_REQUEST_SUBSCRIBE.getMessage());
    }

    @Test
    @DisplayName("실패 케이스: 구독시 구독 상태를 프리미엄 구독에서 일반 구독으로 변경")
    void _7() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.PREMIUM)
                .build();

        // when & then
        assertThatThrownBy(() -> subscribe.modifySubscribeType(SubscribeType.BASIC, HistoryType.SUBSCRIBE))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(FailHttpMessage.BAD_REQUEST_SUBSCRIBE.getMessage());
    }

    @Test
    @DisplayName("실패 케이스: 구독시 구독 상태를 프리미엄 구독에서 구독 안함으로 변경")
    void _8() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.PREMIUM)
                .build();

        // when & then
        assertThatThrownBy(() -> subscribe.modifySubscribeType(SubscribeType.NONE, HistoryType.SUBSCRIBE))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(FailHttpMessage.BAD_REQUEST_SUBSCRIBE.getMessage());
    }

    @Test
    @DisplayName("구독 취소시 구독 상태를 일반 구독에서 구독 안함으로 변경")
    void _9() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.BASIC)
                .build();

        // when
        subscribe.modifySubscribeType(SubscribeType.NONE, HistoryType.CANCEL);

        // then
        assertThat(SubscribeType.NONE).isEqualTo(subscribe.getType());
    }

    @Test
    @DisplayName("구독 취소시 구독 상태를 프리미엄 구독에서 구독 안함으로 변경")
    void _10() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.PREMIUM)
                .build();

        // when
        subscribe.modifySubscribeType(SubscribeType.NONE, HistoryType.CANCEL);

        // then
        assertThat(SubscribeType.NONE).isEqualTo(subscribe.getType());
    }

    @Test
    @DisplayName("구독 취소시 구독 상태를 프리미엄 구독에서 일반 구독으로 변경")
    void _11() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.PREMIUM)
                .build();

        // when
        subscribe.modifySubscribeType(SubscribeType.BASIC, HistoryType.CANCEL);

        // then
        assertThat(SubscribeType.BASIC).isEqualTo(subscribe.getType());
    }

    @Test
    @DisplayName("실패 케이스: 구독 취소시 구독 상태를 구독 안함에서 구독 안함으로 변경")
    void _12() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.NONE)
                .build();

        // when & then
        assertThatThrownBy(() -> subscribe.modifySubscribeType(SubscribeType.NONE, HistoryType.CANCEL))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(FailHttpMessage.BAD_REQUEST_SUBSCRIBE.getMessage());
    }

    @Test
    @DisplayName("실패 케이스: 구독 취소시 구독 상태를 구독 안함에서 일반 구독으로 변경")
    void _13() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.NONE)
                .build();

        // when & then
        assertThatThrownBy(() -> subscribe.modifySubscribeType(SubscribeType.BASIC, HistoryType.CANCEL))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(FailHttpMessage.BAD_REQUEST_SUBSCRIBE.getMessage());
    }

    @Test
    @DisplayName("실패 케이스: 구독 취소시 구독 상태를 구독 안함에서 프리미엄 구독으로 변경")
    void _14() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.NONE)
                .build();

        // when & then
        assertThatThrownBy(() -> subscribe.modifySubscribeType(SubscribeType.PREMIUM, HistoryType.CANCEL))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(FailHttpMessage.BAD_REQUEST_SUBSCRIBE.getMessage());
    }

    @Test
    @DisplayName("실패 케이스: 구독 취소시 구독 상태를 일반 구독에서 프리미엄 구독으로 변경")
    void _15() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.BASIC)
                .build();

        // when & then
        assertThatThrownBy(() -> subscribe.modifySubscribeType(SubscribeType.PREMIUM, HistoryType.CANCEL))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(FailHttpMessage.BAD_REQUEST_SUBSCRIBE.getMessage());
    }

    @Test
    @DisplayName("실패 케이스: 구독 취소시 구독 상태를 일반 구독에서 일반 구독으로 변경")
    void _16() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.BASIC)
                .build();

        // when & then
        assertThatThrownBy(() -> subscribe.modifySubscribeType(SubscribeType.BASIC, HistoryType.CANCEL))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(FailHttpMessage.BAD_REQUEST_SUBSCRIBE.getMessage());
    }

    @Test
    @DisplayName("실패 케이스: 구독 취소시 구독 상태를 프리미엄 구독에서 프리미엄 구독으로 변경")
    void _17() {
        // given
        Subscribe subscribe = Subscribe.builder()
                .type(SubscribeType.PREMIUM)
                .build();

        // when & then
        assertThatThrownBy(() -> subscribe.modifySubscribeType(SubscribeType.PREMIUM, HistoryType.CANCEL))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(FailHttpMessage.BAD_REQUEST_SUBSCRIBE.getMessage());
    }
}