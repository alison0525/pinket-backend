package com.pinket.domain.payment.repository;

import com.pinket.domain.payment.entity.Refund;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundRepository extends JpaRepository<Refund, Long> {

  // 결제의 환불 목록 조회
  List<Refund> findByPaymentId(Long paymentId);
}
