package com.pinket.domain.payment.entity;

import com.pinket.domain.common.entity.BaseEntity;
import com.pinket.domain.order.entity.OrderItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "refund_item")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefundItem extends BaseEntity {

  // 어떤 환불 요청인지 - 단방향 ManyToOne
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "refund_id", nullable = false)
  private Refund refund;

  // 어떤 주문 항목을 환불하는지 - 단방향 ManyToOne
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_item_id", nullable = false)
  private OrderItem orderItem;

  // 환불 수량
  @Column(nullable = false)
  private int quantity;

  // 환불 금액
  @Column(name = "refund_amount", nullable = false)
  private int refundAmount;

  @Builder
  public RefundItem(Refund refund, OrderItem orderItem, int quantity, int refundAmount) {
    this.refund = refund;
    this.orderItem = orderItem;
    this.quantity = quantity;
    this.refundAmount = refundAmount;
  }
}
