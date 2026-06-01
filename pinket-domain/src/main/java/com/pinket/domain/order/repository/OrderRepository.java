package com.pinket.domain.order.repository;

import com.pinket.domain.order.entity.OrderStatus;
import com.pinket.domain.order.entity.Orders;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

  // 단건 조회 (소프트딜리트 고려)
  Optional<Orders> findByIdAndDeletedAtIsNull(Long id);

  // 회원의 주문 목록 (페이징)
  Page<Orders> findByMemberIdAndDeletedAtIsNull(Long memberId, Pageable pageable);

  // 상태별 주문 목록 (만료 처리 배치용, 소프트딜리트 고려)
  List<Orders> findByStatusAndDeletedAtIsNull(OrderStatus status);
}
