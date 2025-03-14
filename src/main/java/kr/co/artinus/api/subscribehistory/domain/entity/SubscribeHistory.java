package kr.co.artinus.api.subscribehistory.domain.entity;

import jakarta.persistence.*;
import kr.co.artinus.api.channel.domain.entity.Channel;
import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.subscribe.domain.entity.Subscribe;
import kr.co.artinus.api.subscribehistory.domain.enumerated.HistoryType;
import kr.co.artinus.global.common.entity.BaseEntity;
import lombok.*;
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

    public void addChannel(Channel channel) {
        this.channel = channel;
        channel.getSubscribeHistories().add(this);
    }

    public void addMember(Member member) {
        this.member = member;
        member.getSubscribeHistories().add(this);
    }

    public void addSubscribe(Subscribe subscribe) {
        this.subscribe = subscribe;
        subscribe.getSubscribeHistories().add(this);
    }

    public static SubscribeHistory create(HistoryType historyType, Member member, Channel channel, Subscribe subscribe) {
        SubscribeHistory subscribeHistory = SubscribeHistory.builder()
                .type(historyType)
                .changedAt(LocalDateTime.now())
                .member(member)
                .channel(channel)
                .subscribe(subscribe)
                .build();

        subscribeHistory.addChannel(channel);
        subscribeHistory.addMember(member);
        subscribeHistory.addSubscribe(subscribe);

        return subscribeHistory;
    }
}
