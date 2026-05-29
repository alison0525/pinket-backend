package com.pinket.domain.ticket.entity;

import com.pinket.domain.common.entity.BaseEntity;
import com.pinket.domain.event.entity.EventSchedule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "schedule_ticket_stock",
    // schedule_id + ticket_type_id 조합 유니크 (같은 일정에 같은 티켓 중복 방지)
    uniqueConstraints = @UniqueConstraint(columnNames = {"schedule_id", "ticket_type_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleTicketStock extends BaseEntity {

  // 어떤 일정인지 - 단방향 ManyToOne
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "schedule_id", nullable = false)
  private EventSchedule schedule;

  // 어떤 티켓 종류인지 - 단방향 ManyToOne
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ticket_type_id", nullable = false)
  private TicketType ticketType;

  // 남은 재고 수량
  @Column(name = "remain_stock", nullable = false)
  private int remainStock;

  @Builder
  public ScheduleTicketStock(EventSchedule schedule, TicketType ticketType, int remainStock) {
    this.schedule = schedule;
    this.ticketType = ticketType;
    this.remainStock = remainStock;
  }

  public void decreaseStock(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("차감할 수량은 1개 이상이어야 합니다.");
    }
    if (this.remainStock < quantity) {
      throw new IllegalArgumentException("남은 티켓 재고가 부족합니다. (현재 재고: " + this.remainStock + ")");
    }
    this.remainStock -= quantity;
  }

  public void increaseStock(int quantity) {
    if (quantity <= 0) {
      throw new IllegalArgumentException("복구할 수량은 1개 이상이어야 합니다.");
    }
    this.remainStock += quantity;
  }
}
