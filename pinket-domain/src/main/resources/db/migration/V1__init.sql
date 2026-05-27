-- PostGIS 확장 활성화 (지리 데이터 처리용)
CREATE EXTENSION IF NOT EXISTS postgis;

-- =============================================
-- member (회원)
-- =============================================
CREATE TABLE member (
                        id                  BIGSERIAL PRIMARY KEY,
                        email               VARCHAR(255) NOT NULL UNIQUE,
                        nickname            VARCHAR(50)  NOT NULL,
                        profile_image_url   VARCHAR(500),
                        oauth_provider      VARCHAR(20)  NOT NULL,
                        oauth_id            VARCHAR(255) NOT NULL,
                        role                VARCHAR(20)  NOT NULL DEFAULT 'USER',
                        deleted_at          TIMESTAMP,
                        created_at          TIMESTAMP    NOT NULL DEFAULT NOW(),
                        updated_at          TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- =============================================
-- event (이벤트)
-- =============================================
CREATE TABLE event (
                       id          BIGSERIAL PRIMARY KEY,
                       host_id     BIGINT       NOT NULL REFERENCES member (id),
                       title       VARCHAR(255) NOT NULL,
                       description TEXT,
                       category    VARCHAR(50)  NOT NULL,
                       status      VARCHAR(20)  NOT NULL DEFAULT 'DRAFT',
                       address     VARCHAR(500) NOT NULL,
                       location    GEOGRAPHY(POINT, 4326) NOT NULL,
                       deleted_at  TIMESTAMP,
                       created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
                       updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- =============================================
-- event_image (이벤트 이미지)
-- =============================================
CREATE TABLE event_image (
                             id           BIGSERIAL PRIMARY KEY,
                             event_id     BIGINT       NOT NULL REFERENCES event (id),
                             image_url    VARCHAR(500) NOT NULL,
                             is_thumbnail BOOLEAN      NOT NULL DEFAULT FALSE,
                             created_at   TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- =============================================
-- event_schedule (이벤트 일정)
-- =============================================
CREATE TABLE event_schedule (
                                id         BIGSERIAL PRIMARY KEY,
                                event_id   BIGINT    NOT NULL REFERENCES event (id),
                                start_at   TIMESTAMP NOT NULL,
                                end_at     TIMESTAMP NOT NULL,
                                created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

-- =============================================
-- event_wishlist (찜)
-- =============================================
CREATE TABLE event_wishlist (
                                id        BIGSERIAL PRIMARY KEY,
                                member_id BIGINT    NOT NULL REFERENCES member (id),
                                event_id  BIGINT    NOT NULL REFERENCES event (id),
                                created_at TIMESTAMP NOT NULL DEFAULT NOW(),
                                UNIQUE (member_id, event_id)
);

-- =============================================
-- ticket_type (티켓 종류)
-- =============================================
CREATE TABLE ticket_type (
                             id          BIGSERIAL PRIMARY KEY,
                             event_id    BIGINT       NOT NULL REFERENCES event (id),
                             name        VARCHAR(100) NOT NULL,
                             price       INT          NOT NULL DEFAULT 0,
                             total_stock INT          NOT NULL,
                             deleted_at  TIMESTAMP,
                             created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
                             updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- =============================================
-- schedule_ticket_stock (일정별 티켓 재고)
-- =============================================
CREATE TABLE schedule_ticket_stock (
                                       id             BIGSERIAL PRIMARY KEY,
                                       schedule_id    BIGINT NOT NULL REFERENCES event_schedule (id),
                                       ticket_type_id BIGINT NOT NULL REFERENCES ticket_type (id),
                                       remain_stock   INT    NOT NULL,
                                       UNIQUE (schedule_id, ticket_type_id)
);

-- =============================================
-- orders (주문)
-- =============================================
CREATE TABLE orders (
                        id           BIGSERIAL PRIMARY KEY,
                        member_id    BIGINT      NOT NULL REFERENCES member (id),
                        status       VARCHAR(20) NOT NULL DEFAULT 'PENDING',
                        total_amount INT         NOT NULL DEFAULT 0,
                        deleted_at   TIMESTAMP,
                        created_at   TIMESTAMP   NOT NULL DEFAULT NOW(),
                        updated_at   TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- =============================================
-- order_status_history (주문 상태 변경 이력)
-- =============================================
CREATE TABLE order_status_history (
                                      id         BIGSERIAL PRIMARY KEY,
                                      order_id   BIGINT      NOT NULL REFERENCES orders (id),
                                      status     VARCHAR(20) NOT NULL,
                                      created_at TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- =============================================
-- order_item (주문 항목)
-- =============================================
CREATE TABLE order_item (
                            id             BIGSERIAL PRIMARY KEY,
                            order_id       BIGINT    NOT NULL REFERENCES orders (id),
                            ticket_type_id BIGINT    NOT NULL REFERENCES ticket_type (id),
                            schedule_id    BIGINT    NOT NULL REFERENCES event_schedule (id),
                            quantity       INT       NOT NULL,
                            unit_price     INT       NOT NULL,
                            deleted_at     TIMESTAMP,
                            created_at     TIMESTAMP NOT NULL DEFAULT NOW()
);

-- =============================================
-- payment (결제)
-- =============================================
CREATE TABLE payment (
                         id                BIGSERIAL PRIMARY KEY,
                         order_id          BIGINT       NOT NULL REFERENCES orders (id),
                         pg_transaction_id VARCHAR(255) UNIQUE,
                         method            VARCHAR(50),
                         amount            INT          NOT NULL,
                         status            VARCHAR(20)  NOT NULL DEFAULT 'READY',
                         paid_at           TIMESTAMP,
                         created_at        TIMESTAMP    NOT NULL DEFAULT NOW(),
                         updated_at        TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- =============================================
-- refund (환불)
-- =============================================
CREATE TABLE refund (
                        id         BIGSERIAL PRIMARY KEY,
                        payment_id BIGINT      NOT NULL REFERENCES payment (id),
                        reason     TEXT,
                        status     VARCHAR(20) NOT NULL DEFAULT 'REQUESTED',
                        created_at TIMESTAMP   NOT NULL DEFAULT NOW(),
                        updated_at TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- =============================================
-- refund_item (환불 항목)
-- =============================================
CREATE TABLE refund_item (
                             id            BIGSERIAL PRIMARY KEY,
                             refund_id     BIGINT    NOT NULL REFERENCES refund (id),
                             order_item_id BIGINT    NOT NULL REFERENCES order_item (id),
                             quantity      INT       NOT NULL,
                             refund_amount INT       NOT NULL,
                             created_at    TIMESTAMP NOT NULL DEFAULT NOW()
);

-- =============================================
-- notification (알림)
-- =============================================
CREATE TABLE notification (
                              id         BIGSERIAL PRIMARY KEY,
                              member_id  BIGINT       NOT NULL REFERENCES member (id),
                              type       VARCHAR(50)  NOT NULL,
                              title      VARCHAR(255) NOT NULL,
                              content    TEXT,
                              is_read    BOOLEAN      NOT NULL DEFAULT FALSE,
                              created_at TIMESTAMP    NOT NULL DEFAULT NOW()
);