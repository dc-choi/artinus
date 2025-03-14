package kr.co.artinus.api.subscribe.domain.entity;

import jakarta.persistence.*;
import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.subscribe.domain.enumerated.SubscribeType;
import kr.co.artinus.api.subscribehistory.domain.entity.SubscribeHistory;
import kr.co.artinus.api.subscribehistory.domain.enumerated.HistoryType;
import kr.co.artinus.global.common.entity.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "subscribes")
public class Subscribe extends BaseEntity {
    @Comment("구독 타입")
    @Column(name = "type", columnDefinition = "VARCHAR(50)")
    @Enumerated(EnumType.STRING)
    private SubscribeType type;

    @Comment("회원 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", columnDefinition = "BIGINT", foreignKey = @ForeignKey(name = "fk_subscribe_member"))
    private Member member;

    @Builder.Default
    @Comment("구독 내역")
    @OneToMany(mappedBy = "subscribe")
    @ToString.Exclude
    private List<SubscribeHistory> subscribeHistories = new ArrayList<>();

    public void modifySubscribeType(SubscribeType subscribeType, HistoryType historyType) {
        switch (historyType) {
            // 구독 안함 -> 일반 구독
            // 구독 안함 -> 프리미엄 구독
            // 일반 구독 -> 프리미엄 구독
            case SUBSCRIBE -> {
                switch (this.type) {
                    case NONE, BASIC -> {
                        if (subscribeType == SubscribeType.NONE) {
                            throw new IllegalStateException("구독을 취소할 수 없습니다.");
                        }
                    }
                    case PREMIUM -> {
                        if (subscribeType != SubscribeType.PREMIUM) {
                            throw new IllegalStateException("구독 등급을 낮출 수 없습니다.");
                        }
                    }
                }
            }
            // 프리미엄 구독 -> 일반 구독
            // 프리미엄 구독 -> 구독 안함
            // 일반 구독 -> 구독 안함
            case CANCEL -> {
                switch (this.type) {
                    case NONE -> throw new IllegalStateException("구독을 취소할 수 없습니다.");
                    case BASIC -> {
                        if (subscribeType != SubscribeType.NONE) {
                            throw new IllegalStateException("구독 등급을 높일 수 없습니다.");
                        }
                    }
                    case PREMIUM -> {
                        if (subscribeType == SubscribeType.PREMIUM) {
                            throw new IllegalStateException("구독 등급을 높일 수 없습니다.");
                        }
                    }
                }
            }
        }

        // 구독 상태를 변경
        this.type = subscribeType;
    }
}
