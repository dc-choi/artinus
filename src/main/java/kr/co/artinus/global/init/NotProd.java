package kr.co.artinus.global.init;

import kr.co.artinus.api.channel.domain.entity.Channel;
import kr.co.artinus.api.channel.domain.enumerated.ChannelRole;
import kr.co.artinus.api.channel.domain.persistence.ChannelRepository;
import kr.co.artinus.api.member.domain.entity.Member;
import kr.co.artinus.api.member.domain.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class NotProd {
    private final ChannelRepository channelRepository;
    private final MemberRepository memberRepository;

    @Bean
    @Order(1)
    public ApplicationRunner init() {
        return args -> {
            data();
        };
    }

    public void data() {
        Channel homePage = Channel.builder()
                .name("홈페이지")
                .role(ChannelRole.EVERY)
                .build();

        Channel app = Channel.builder()
                .name("모바일앱")
                .role(ChannelRole.EVERY)
                .build();

        Channel naver = Channel.builder()
                .name("네이버")
                .role(ChannelRole.SUBSCRIBE)
                .build();

        Channel SKT = Channel.builder()
                .name("SKT")
                .role(ChannelRole.SUBSCRIBE)
                .build();

        Channel KT = Channel.builder()
                .name("KT")
                .role(ChannelRole.SUBSCRIBE)
                .build();

        Channel LG = Channel.builder()
                .name("LGU+")
                .role(ChannelRole.SUBSCRIBE)
                .build();

        Channel phone = Channel.builder()
                .name("콜센터")
                .role(ChannelRole.CANCEL)
                .build();

        Channel chat = Channel.builder()
                .name("채팅상담")
                .role(ChannelRole.CANCEL)
                .build();

        Channel email = Channel.builder()
                .name("이메일")
                .role(ChannelRole.CANCEL)
                .build();

        channelRepository.saveAll(List.of(
                homePage,
                app,
                naver,
                SKT,
                KT,
                LG,
                phone,
                chat,
                email
        ));

        Member member = Member.builder()
                .phone("1012345678")
                .build();

        memberRepository.save(member);
    }
}
