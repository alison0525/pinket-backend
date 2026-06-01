package com.pinket.domain.event.repository;

import com.pinket.domain.event.entity.Event;
import com.pinket.domain.event.entity.EventStatus;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

  // 단건 조회 (소프트딜리트 고려)
  Optional<Event> findByIdAndDeletedAtIsNull(Long id);

  // 호스트가 등록한 이벤트 목록 (페이징)
  Page<Event> findByHostIdAndDeletedAtIsNull(Long hostId, Pageable pageable);

  // 상태별 이벤트 목록 (페이징)
  Page<Event> findByStatusAndDeletedAtIsNull(EventStatus status, Pageable pageable);
}
