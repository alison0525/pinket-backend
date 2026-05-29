package com.pinket.domain.ticket.entity;

import com.pinket.domain.common.entity.BaseEntity;
import com.pinket.domain.event.entity.Event;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ticket_type")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketType extends BaseEntity {

  // 어떤 이벤트의 티켓인지 - 단방향 ManyToOne
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  // 티켓 종류 이름 (예: VIP, 일반, 얼리버드)
  @Column(nullable = false, length = 100)
  private String name;

  // 티켓 가격 (0이면 무료)
  @Column(nullable = false)
  private int price;

  // 총 재고 수량
  @Column(name = "total_stock", nullable = false)
  private int totalStock;

  // 소프트딜리트
  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Builder
  public TicketType(Event event, String name, int price, int totalStock) {
    if (price < 0) {
      throw new IllegalArgumentException("티켓 가격은 음수가 될 수 없습니다.");
    }
    if (totalStock <= 0) {
      throw new IllegalArgumentException("총 재고 수량은 1개 이상이어야 합니다.");
    }
    this.event = event;
    this.name = name;
    this.price = price;
    this.totalStock = totalStock;
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }
}
