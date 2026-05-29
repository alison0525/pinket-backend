plugins {
    id("org.springframework.boot") // 실행 플러그인 활성화
}

dependencies {
    implementation(project(":pinket-common"))
    implementation(project(":pinket-domain"))
    implementation(project(":pinket-infra"))

    // 유저들과 HTTP로 대화하는 웹 도구
    implementation("org.springframework.boot:spring-boot-starter-web")

    // JPA Auditing (@EnableJpaAuditing) 사용을 위해 직접 추가
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    // Lombok
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
}