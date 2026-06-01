package com.pinket.domain.payment.repository;

import com.pinket.domain.payment.entity.Payment;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

  // 주문으로 결제 조회
  Optional<Payment> findByOrderId(Long orderId);

  // PG사 거래 ID로 결제 조회 (PG 콜백 처리용)
  Optional<Payment> findByPgTransactionId(String pgTransactionId);
}
