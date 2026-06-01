package com.pinket.domain.order.repository;

import com.pinket.domain.order.entity.OrderItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

  // 주문의 항목 목록 조회 (소프트딜리트 고려)
  List<OrderItem> findByOrderIdAndDeletedAtIsNull(Long orderId);
}
