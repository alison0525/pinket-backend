package com.pinket.domain.ticket.repository;

import com.pinket.domain.ticket.entity.ScheduleTicketStock;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleTicketStockRepository extends JpaRepository<ScheduleTicketStock, Long> {

  // 일정 + 티켓 종류로 재고 조회
  Optional<ScheduleTicketStock> findByScheduleIdAndTicketTypeId(Long scheduleId, Long ticketTypeId);
}
