package com.pinket.domain.event.entity;

import com.pinket.domain.common.entity.BaseEntity;
import com.pinket.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    name = "event_wishlist",
    uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "event_id"}))
public class EventWishlist extends BaseEntity {
  // 찜한 회원 - 단방향 ManyToOne
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  // 찜한 이벤트 - 단방향 ManyToOne
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @Builder
  public EventWishlist(Member member, Event event) {
    this.member = member;
    this.event = event;
  }
}
