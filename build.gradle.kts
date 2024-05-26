plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	id("org.openapi.generator") version "7.0.0"
	idea
}

group = "com.xpcoffee"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

dependencies {
	// base
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// open-api generation
	implementation("io.swagger.core.v3:swagger-annotations:2.2.10")
	implementation("jakarta.validation:jakarta.validation-api:3.0.0")
	implementation("jakarta.annotation:jakarta.annotation-api:3.0.0")
	implementation("org.openapitools:jackson-databind-nullable:0.2.6")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

sourceSets {
    main {
        java {
			srcDir("$projectDir/build/generated-resources/src/main/java/")
        }
    }
}

openApiGenerate {
	generatorName.set("spring")
	inputSpec.set("$projectDir/src/main/resources/openapi.yaml")
	outputDir.set(layout.buildDirectory.file("generated-resources").get().toString())
	invokerPackage.set("com.xpcoffee.rock_paper_scissors.api")
	apiPackage.set("com.xpcoffee.rock_paper_scissors.api.generated")
	modelPackage.set("com.xpcoffee.rock_paper_scissors.api.generated.model")
	configOptions.set(mapOf(
		"interfaceOnly" to "true",
		"skipDefaultInterface" to "true",
		"useSpringBoot3" to "true"
	))
}
