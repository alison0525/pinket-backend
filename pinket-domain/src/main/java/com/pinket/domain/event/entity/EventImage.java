package com.pinket.domain.event.entity;

import com.pinket.domain.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "event_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EventImage extends BaseEntity {

  // 어떤 이벤트의 이미지인지 - 단방향 ManyToOne
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @Column(name = "image_url", nullable = false, length = 500)
  private String imageUrl;

  // 대표 썸네일 여부
  @Column(name = "is_thumbnail", nullable = false)
  private boolean isThumbnail;

  @Builder
  public EventImage(Event event, String imageUrl, boolean isThumbnail) {
    this.event = event;
    this.imageUrl = imageUrl;
    this.isThumbnail = isThumbnail;
  }
}
