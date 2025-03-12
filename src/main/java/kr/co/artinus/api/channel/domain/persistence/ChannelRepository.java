package kr.co.artinus.api.channel.domain.persistence;

import kr.co.artinus.api.channel.domain.entity.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {}
