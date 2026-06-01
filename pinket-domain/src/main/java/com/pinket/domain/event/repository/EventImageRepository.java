package com.pinket.domain.event.repository;

import com.pinket.domain.event.entity.EventImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventImageRepository extends JpaRepository<EventImage, Long> {

    // 섬네일 먼저 정렬
    List<EventImage> findByEventIdOrderByIsThumbnailDesc(Long eventId);
}
