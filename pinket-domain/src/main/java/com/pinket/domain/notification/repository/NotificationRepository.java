package com.pinket.domain.notification.repository;

import com.pinket.domain.notification.entity.Notification;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

  // 알림은 무한 스크롤이라 count 쿼리 불필요 → Slice 사용
  Slice<Notification> findByMemberIdOrderByCreatedAtDesc(Long memberId, Pageable pageable);

  // 읽지 않은 알림 목록
  List<Notification> findByMemberIdAndIsReadFalse(Long memberId);
}
