package kr.co.artinus.api.member.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import kr.co.artinus.api.subscribe.domain.entity.Subscribe;
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
@Table(name = "members")
public class Member extends BaseEntity {
    @Comment("회원 연락처")
    @Column(name = "phone", columnDefinition = "VARCHAR(50)")
    private String phone;

    @Builder.Default
    @Comment("현 구독")
    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<Subscribe> subscribes = new ArrayList<>();

    @Builder.Default
    @Comment("구독 내역")
    @OneToMany(mappedBy = "member")
    @ToString.Exclude
    private List<SubscribeHistory> subscribeHistories = new ArrayList<>();
}
