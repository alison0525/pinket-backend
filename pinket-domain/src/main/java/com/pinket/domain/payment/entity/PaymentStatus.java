package com.pinket.domain.payment.entity;

public enum PaymentStatus {
  READY, // 결제 대기
  DONE, // 결제 완료
  CANCELLED, // 결제 취소
  FAILED // 결제 실패
}
