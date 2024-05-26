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
