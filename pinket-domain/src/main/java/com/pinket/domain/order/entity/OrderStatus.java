package com.pinket.domain.order.entity;

public enum OrderStatus {
    PENDING,    // 결제 대기 (5분 TTL)
    PAID,       // 결제 완료
    CANCELLED,  // 취소
    EXPIRED     // 만료 (5분 내 미결제)
}
