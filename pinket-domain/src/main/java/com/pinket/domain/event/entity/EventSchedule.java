package com.pinket.domain.event.entity;

import com.pinket.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event_schedule")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventSchedule extends BaseEntity {

  // 어떤 이벤트의 일정인지 - 단방향 ManyToOne
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  // 이벤트 시작 시간
  @Column(name = "start_at", nullable = false)
  private LocalDateTime startAt;

  // 이벤트 종료 시간
  @Column(name = "end_at", nullable = false)
  private LocalDateTime endAt;

  @Builder
  public EventSchedule(Event event, LocalDateTime startAt, LocalDateTime endAt) {
    this.event = event;
    this.startAt = startAt;
    this.endAt = endAt;
  }
}
