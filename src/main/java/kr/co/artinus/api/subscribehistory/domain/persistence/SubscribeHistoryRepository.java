package kr.co.artinus.api.subscribehistory.domain.persistence;

import jakarta.persistence.Tuple;
import kr.co.artinus.api.subscribehistory.domain.entity.SubscribeHistory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubscribeHistoryRepository extends JpaRepository<SubscribeHistory, Long> {
    @Query("""
    SELECT 
        sh.channel.id AS channelId,
        MIN(CASE WHEN sh.type = 'SUBSCRIBE' THEN sh.changedAt END) AS startDate,
        MAX(CASE WHEN sh.type = 'UNSUBSCRIBE' THEN sh.changedAt END) AS endDate
    FROM SubscribeHistory sh
    WHERE sh.member.id = :memberId
    GROUP BY sh.member.id, sh.channel.id
    """)
    List<Tuple> findHistory(@Param("memberId") Long memberId, Pageable pageable);
}
