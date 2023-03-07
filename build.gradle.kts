import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.2.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
}

group = "com.futi"
version = "0.0.2-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:2.1.1")
    implementation("com.google.code.gson:gson:2.8.6")
//    implementation("org.springframework.security.oauth:spring-security-oauth2:2.3.3.RELEASE") {
//        exclude(group = "org.junit.vintage", module = "jackson-mapper-asl")
//    }
    implementation("org.springframework.security.oauth:spring-security-oauth2:2.3.8.RELEASE") {
        exclude(group = "org.junit.vintage", module = "jackson-mapper-asl")
    }
    implementation("org.apache.tomcat:tomcat-jdbc:9.0.29")
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("io.springfox:springfox-swagger-ui:2.9.2")
    implementation("io.springfox:springfox-bean-validators:2.9.2")

    implementation("javax.xml.bind:jaxb-api:2.3.0")
    implementation("javax.activation:activation:1.1")
    implementation("com.sun.xml.bind:jaxb-core:2.3.0")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.0")
    implementation("org.json:json:20190722")

    runtimeOnly("org.springframework.boot:spring-boot-devtools")

    implementation("log4j:log4j:1.2.17")
    runtimeOnly("mysql:mysql-connector-java")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "jackson-mapper-asl")
    }

}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
