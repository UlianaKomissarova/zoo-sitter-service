plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
	kotlin("plugin.jpa") version "1.9.25"
	id("org.jetbrains.kotlin.kapt") version "1.9.25"
	id("org.liquibase.gradle") version "2.2.0"
}

group = "dev.uliana"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.mapstruct:mapstruct:1.6.3")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("jakarta.validation:jakarta.validation-api:3.1.0")
	kapt("org.mapstruct:mapstruct-processor:1.6.3")
	implementation("org.liquibase:liquibase-core:4.30.0") {
		exclude(group = "javax.xml.bind", module = "jaxb-api")
	}
	liquibaseRuntime("org.liquibase:liquibase-core:4.30.0") {
		exclude(group = "javax.xml.bind", module = "jaxb-api")
	}
	liquibaseRuntime("info.picocli:picocli:4.7.6")
	liquibaseRuntime("org.postgresql:postgresql")
	runtimeOnly("org.postgresql:postgresql")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("io.mockk:mockk:1.9.3")
	testImplementation("org.testcontainers:postgresql:1.20.4")
	testImplementation("org.testcontainers:junit-jupiter:1.20.4")
	testImplementation("org.springframework.boot:spring-boot-testcontainers:3.4.0")
}

liquibase {
	activities {
		register("main") {
			arguments = mapOf(
					"changelogFile" to "src/main/resources/postgres.db.changelog/db.changelog-master.yaml",
					"url" to "jdbc:postgresql://localhost:5435/postgres_db",
					"username" to "user",
					"password" to "pass"
			)
		}
	}
	runList = "main"
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
