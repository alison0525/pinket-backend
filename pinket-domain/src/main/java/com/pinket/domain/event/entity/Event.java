package com.pinket.domain.event.entity;

import com.pinket.domain.common.entity.BaseEntity;
import com.pinket.domain.member.entity.Member;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Entity
@Table(name = "event")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "host_id")
  private Member host;

  @Column(nullable = false)
  private String title;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(nullable = false, length = 50)
  private String category;

  // Enum으로 타입 안전성 확보 (DRAFT, OPEN, CLOSED, CANCELLED)
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private EventStatus status;

  @Column(nullable = false, length = 500)
  private String address;

  // POINT, 점 선 면 중에서 지도 위희 한 점만 저장
  // 4326, SRID: 공간 참조 시스템 식별자 번호
  @Column(columnDefinition = "geography(POINT, 4326)", nullable = false)
  // 하이버네이트 6에 도입된 기능
  // db에서는 geography로 저장되어 있지만 자바에서는 이진 데이터 포멧으로 변환 가공
  @JdbcTypeCode(SqlTypes.GEOMETRY)
  private Point location;

  // 소프트딜리트 - null이면 활성, 값이 있으면 삭제
  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Builder
  public Event(
      Member host,
      String title,
      String description,
      String category,
      EventStatus status,
      String address,
      Point location) {
    this.host = host;
    this.title = title;
    this.description = description;
    this.category = category;
    // 빌더 호출 시 status 누락되면 기본값 DRAFT
    this.status = status != null ? status : EventStatus.DRAFT;
    this.address = address;
    this.location = location;
  }

  // 이벤트 상태 변경 비즈니스 메서드
  public void open() {
    this.status = EventStatus.OPEN;
  }

  public void close() {
    this.status = EventStatus.CLOSED;
  }

  public void cancel() {
    this.status = EventStatus.CANCELLED;
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }

  // 위도/경도로 Point 객체 생성하는 팩토리 메서드
  // JTS Point는 x=경도(Lng), y=위도(Lat) 순서여서 익숙한 위도 경도 순으로 변경
  public static Point createPoint(double latitude, double longitude) {
    if (latitude < -90.0 || latitude > 90.0) {
      throw new IllegalArgumentException("위도는 -90도에서 90도 사이여야 합니다.");
    }
    if (longitude < -180.0 || longitude > 180.0) {
      throw new IllegalArgumentException("경도는 -180도에서 180도 사이여야 합니다.");
    }
    // 공간 객체를 만드는 팩토리 메서드 사용
    // new PrecisionModel: 좌표의 정밀도 설정, 기본값 세팅 시 소수점 아래까지 정밀하게 표현
    // 4326: 공간 참조 시스템 식별자
    GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    // new Coordinate(longitude, latitude) 여디서는 경도, 위도 순서로 받음
    // 익숙한 위도 경도 순으로 변경하여 실수를 방지
    return geometryFactory.createPoint(new Coordinate(longitude, latitude));
  }
}
