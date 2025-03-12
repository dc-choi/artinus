package kr.co.artinus.api.subscribe.history.domain.entity;

import jakarta.persistence.*;
import kr.co.artinus.api.channel.domain.entity.Channel;
import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.subscribe.domain.entity.Subscribe;
import kr.co.artinus.api.subscribe.history.domain.enumerated.HistoryType;
import kr.co.artinus.global.common.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "subscribe_historys")
public class SubscribeHistory extends BaseEntity {
    @Comment("내역 타입")
    @Column(name = "type", columnDefinition = "VARCHAR(50)")
    @Enumerated(EnumType.STRING)
    private HistoryType type;

    @Comment("구독 변경일")
    @Column(name = "changed_at", columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime changedAt;

    @Comment("회원 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(name = "fk_subscribe_history_member"))
    private Member member;

    @Comment("채널 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(name = "fk_subscribe_history_channel"))
    private Channel channel;

    @Comment("구독 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribe_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(name = "fk_subscribe_history_subscribe"))
    private Subscribe subscribe;
}
