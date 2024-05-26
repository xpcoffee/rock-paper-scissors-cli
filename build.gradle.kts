plugins {
	java
	idea
	application
}

group = "com.xpcoffee"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_21
	targetCompatibility = JavaVersion.VERSION_21
}

repositories {
	mavenCentral()
}

tasks.named("run", JavaExec::class) {
	standardInput = System.`in`
}

dependencies {
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.1")
	testImplementation("org.junit.jupiter:junit-jupiter-params:5.5.1")
	testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

application {
	mainClass = "com.xpcoffee.rock_paper_scissors.RockPaperScissorsCli"
}

tasks.withType<Test> {
	useJUnitPlatform()

	testLogging {
		events("failed", "standardOut")
	}
}
