package com.pinket.domain.order.entity;

import com.pinket.domain.common.entity.BaseEntity;
import com.pinket.domain.event.entity.EventSchedule;
import com.pinket.domain.ticket.entity.TicketType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem extends BaseEntity {

    // 어떤 주문인지 - 단방향 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    // 어떤 티켓 종류인지 - 단방향 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_type_id", nullable = false)
    private TicketType ticketType;

    // 어떤 일정인지 - 단방향 ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private EventSchedule schedule;

    // 구매 수량
    @Column(nullable = false)
    private int quantity;

    // 구매 당시 단가 (나중에 티켓 가격이 바뀌어도 구매 시점 가격 보존)
    @Column(name = "unit_price", nullable = false)
    private int unitPrice;

    // 소프트딜리트
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Builder
    public OrderItem(
            Orders order,
            TicketType ticketType,
            EventSchedule schedule,
            int quantity,
            int unitPrice) {
        this.order = order;
        this.ticketType = ticketType;
        this.schedule = schedule;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}
