package com.pinket.domain.payment.repository;

import com.pinket.domain.payment.entity.RefundItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundItemRepository extends JpaRepository<RefundItem, Long> {

  // 환불의 항목 목록 조회
  List<RefundItem> findByRefundId(Long refundId);
}
