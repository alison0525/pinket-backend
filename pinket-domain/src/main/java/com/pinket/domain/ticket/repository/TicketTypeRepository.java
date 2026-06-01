package com.pinket.domain.ticket.repository;

import com.pinket.domain.ticket.entity.TicketType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketTypeRepository extends JpaRepository<TicketType, Long> {

  // 이벤트의 티켓 종류 목록 조회 (소프트딜리트 고려)
  List<TicketType> findByEventIdAndDeletedAtIsNull(Long eventId);
}
