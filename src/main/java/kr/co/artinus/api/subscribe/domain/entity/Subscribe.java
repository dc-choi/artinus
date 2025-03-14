package kr.co.artinus.api.subscribe.domain.entity;

import jakarta.persistence.*;
import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.subscribe.domain.enumerated.SubscribeType;
import kr.co.artinus.api.subscribehistory.domain.entity.SubscribeHistory;
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

    public void modifySubscribeType(SubscribeType type) {
        // 구독 안함 -> 일반 구독, 구독 안함 -> 프리미엄 구독, 일반 구독 -> 프리미엄 구독
        switch (this.type) {
            case NONE -> {
                if (type == SubscribeType.NONE) {
                    throw new IllegalArgumentException("구독을 취소할 수 없습니다.");
                }
            }
            case BASIC, PREMIUM -> {
                if (type != SubscribeType.PREMIUM) {
                    throw new IllegalArgumentException("구독 등급을 낮출 수 없습니다.");
                }
            }
        }

        // 구독 상태를 변경
        this.type = type;
    }
}
