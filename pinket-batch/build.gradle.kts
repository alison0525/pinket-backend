plugins {
    id("org.springframework.boot") // 실행 플러그인 활성화
}

dependencies {
    implementation(project(":pinket-common"))
    implementation(project(":pinket-domain"))
    implementation(project(":pinket-infra"))

    // 이 모듈은 유저들과 HTTP로 대화해야 하므로 웹 도구를 얹어줍니다.
    implementation("org.springframework.boot:spring-boot-starter-web")
}