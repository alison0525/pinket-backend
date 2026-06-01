package com.pinket.domain.event.repository;

import com.pinket.domain.event.entity.EventSchedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventScheduleRepository extends JpaRepository<EventSchedule, Long> {

  // 이벤트의 전체 일정 조회 (관리자, 호스트용)
  List<EventSchedule> findByEventId(Long eventId);

  // 현재 시간 이후 일정만 조회 (일반 유저용)
  List<EventSchedule> findByEventIdAndStartAtAfter(Long eventId, LocalDateTime now);
}
