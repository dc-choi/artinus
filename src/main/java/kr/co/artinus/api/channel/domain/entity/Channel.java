package kr.co.artinus.api.channel.domain.entity;

import jakarta.persistence.*;
import kr.co.artinus.api.channel.domain.enumerated.ChannelRole;
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
@Table(name = "channels")
public class Channel extends BaseEntity {
    @Comment("채널 명")
    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @Comment("채널 권한")
    @Column(name = "role", columnDefinition = "VARCHAR(50)")
    @Enumerated(EnumType.STRING)
    private ChannelRole role;

    @Builder.Default
    @Comment("구독 내역")
    @OneToMany(mappedBy = "channel")
    @ToString.Exclude
    private List<SubscribeHistory> subscribeHistories = new ArrayList<>();
}
