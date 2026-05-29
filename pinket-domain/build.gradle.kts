dependencies {
    // 가장 하위 모듈인 common만 가져와서 내 몸집에 합칩니다.
    implementation(project(":pinket-common"))

    // JPA - 엔티티, Repository 사용을 위한 ORM 라이브러리
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Flyway 코어 - DB 스키마 버전 관리 툴
    implementation("org.flywaydb:flyway-core")

    // Flyway PostgreSQL 전용 드라이버 (Flyway 10부터 DB별로 따로 추가해야 함)
    implementation("org.flywaydb:flyway-database-postgresql")

    // PostGIS geography 타입을 JPA 엔티티에서 사용하기 위한 라이브러리
    implementation("org.hibernate.orm:hibernate-spatial")

    // PostgreSQL JDBC 드라이버 - 런타임에만 필요 (컴파일 때는 불필요)
    runtimeOnly("org.postgresql:postgresql")

    // Lombok - getter/setter/builder 등 보일러플레이트 코드 자동 생성
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}