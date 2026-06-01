package com.pinket.domain.order.repository;

import com.pinket.domain.order.entity.OrderStatusHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusHistoryRepository extends JpaRepository<OrderStatusHistory, Long> {

  // 주문 상태 변경 이력 조회
  List<OrderStatusHistory> findByOrderIdOrderByCreatedAtAsc(Long orderId);
}
