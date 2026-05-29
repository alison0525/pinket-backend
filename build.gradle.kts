// =========================================================================
// [1단계] 플러그인 사전 등록 (버전 중앙 제어실)
// =========================================================================
plugins {
    // 코틀린 기본 자바 플러그인
    java

    // Spring Boot 플러그인 (apply false = 루트에는 켜지 않고, 버전만 4.0.6으로 박아둠)
    id("org.springframework.boot") version "4.0.6" apply false

    // 스프링 의존성 관리 플러그인 (apply false = 마찬가지로 루트엔 안 켜고 버전만 1.1.7로 대기)
    id("io.spring.dependency-management") version "1.1.7" apply false

    // 코드 포맷팅 자동화 플러그인 (apply false = 버전만 등록, 실제 적용은 subprojects에서)
    id("com.diffplug.spotless") version "7.0.3" apply false

}

// =========================================================================
// [2단계] 기본 프로젝트 정보 설정
// =========================================================================
group = "com.pinket"
version = "0.0.1-SNAPSHOT"

// =========================================================================
// [3단계] 전체 프로젝트 (루트 + 모든 자식 모듈) 공통 정보 상속
// =========================================================================
allprojects {
    group = "com.pinket"
    version = "0.0.1-SNAPSHOT"
}

// =========================================================================
// [4단계] 서브모듈(자식 폴더)들에게만 적용하는 공통 배포 장비
// =========================================================================
subprojects {

    // [규칙 1] 모든 자식 모듈들은 기본적으로 '자바 프로그램' 임을 선언 (컴파일 가능하게)
    apply(plugin = "java")

    // [규칙 2] 모든 자식 모듈들에게 버전 관리 매니저(dependency-management) 장착
    apply(plugin = "io.spring.dependency-management")

    // [규칙 3] 모든 자식 모듈에 Spotless 코드 포맷팅 도구 장착
    apply(plugin = "com.diffplug.spotless")


    // [규칙 5] 모든 자식 모듈들의 자바 버전을 무조건 'Java 25'로 강제 통일
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(25)
        }
    }

    // [규칙 6] 오픈소스 라이브러리를 다운로드받을 중앙 저장소 지정
    repositories {
        mavenCentral()
    }

    // [규칙 7] Kotlin DSL 컴파일 타임 에러 해결을 위한 치트키 블록
    configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
        imports {
            // 스프링 부트 4.0.6 버전에 맞는 완벽한 라이브러리 조합 지도(BOM) 주입
            mavenBom("org.springframework.boot:spring-boot-dependencies:4.0.6")
        }
    }

    // [규칙 8] Spotless 세부 규칙 설정
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        java {
            // Google Java Style Guide 기준으로 코드 자동 정렬
            googleJavaFormat("1.35.0")
            // 사용하지 않는 import 자동 제거
            removeUnusedImports()
            // 줄 끝 불필요한 공백 제거
            trimTrailingWhitespace()
            // 파일 마지막에 개행 추가
            endWithNewline()
        }
    }


    // [규칙 9] 모든 자식 모듈에 기본 탑재할 공통 테스트 도구
    dependencies {
        // 스프링 부트의 기본 테스트 세트 강제 보급
        "testImplementation"("org.springframework.boot:spring-boot-starter-test")
        // JUnit5 기반 테스트 엔진 실행용 런처 보급
        "testRuntimeOnly"("org.junit.platform:junit-platform-launcher")
    }

    // [규칙 10] 테스트를 실행할 때 최신 JUnit5(Platform)를 쓰도록 고정
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}