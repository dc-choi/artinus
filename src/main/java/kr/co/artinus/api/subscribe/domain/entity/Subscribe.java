package kr.co.artinus.api.subscribe.domain.entity;

import jakarta.persistence.*;
import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.subscribe.domain.enumerated.SubscribeType;
import kr.co.artinus.api.subscribe.history.domain.entity.SubscribeHistory;
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
}
