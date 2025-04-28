plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.4"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
	jacoco
}

jacoco {
	toolVersion = "0.8.13"
	reportsDirectory = layout.buildDirectory.dir("customJacocoReportDir")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-amqp")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	compileOnly("org.projectlombok:lombok")
	runtimeOnly("com.h2database:h2")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.amqp:spring-rabbit-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
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

tasks.test {
	useJUnitPlatform()
	finalizedBy(tasks.jacocoTestReport, tasks.jacocoTestCoverageVerification)
}

tasks.jacocoTestReport {
	dependsOn(tasks.test)
	reports {
		xml.required.set(true)
		html.required.set(true)
		csv.required.set(false)
	}
	classDirectories.setFrom(
		files(classDirectories.files.map {
			fileTree(it) {
				exclude("com/example/controller/**")
				exclude("com/example/datalayer/**")
				exclude("com/example/dto/**")
				include("com/example/servicelayer/**")
				exclude("com/example/rabbitmq/**")
				exclude("com/example/PokemonMicroserviceApplication.kt")
			}
		})
	)
}

tasks.jacocoTestCoverageVerification {
	dependsOn(tasks.test)
	violationRules {
		rule {
			limit {
				minimum = "0.8".toBigDecimal()
			}
		}
	}
	classDirectories.setFrom(
		files(classDirectories.files.map {
			fileTree(it) {
				exclude("com/example/controller/**")
				exclude("com/example/datalayer/**")
				exclude("com/example/dto/**")
				include("com/example/servicelayer/**")
				exclude("com/example/rabbitmq/**")
				exclude("com/example/PokemonMicroserviceApplication.kt")
			}
		})
	)
}

tasks.check {
	dependsOn(tasks.jacocoTestCoverageVerification)
}
