package com.pinket.domain.event.entity;

public enum EventStatus {
    DRAFT, //초안 - 호스트가 작성 중, 아직 공개 안 됨
    OPEN, //공개 - 티켓 판매 중
    CLOSED, //마감 - 티켓 판매 종료
    CANCELLED //취소 - 이벤트 자체가 취소됨
}