package com.pinket.domain.event.repository;

import com.pinket.domain.event.entity.EventWishlist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventWishlistRepository extends JpaRepository<EventWishlist, Long> {

  // 특정 회원이 특정 이벤트 찜했는지 확인 (삭제된 이벤트 제외)
  Optional<EventWishlist> findByMemberIdAndEventIdAndEventDeletedAtIsNull(Long memberId, Long eventId);

  // 특정 회원의 찜 목록 (삭제된 이벤트 제외)
  List<EventWishlist> findByMemberIdAndEventDeletedAtIsNull(Long memberId);
}
