plugins {
	java
	id("org.springframework.boot") version "3.2.3"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

dependencies {

	// added manually
	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")
	//  Temporary explicit version to fix Thymeleaf bug
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6:3.1.1.RELEASE")

	implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
