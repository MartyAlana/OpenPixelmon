plugins {
	id("fabric-loom") version "0.6.18"
}

group = "me.marty"
version = "1.0.0-SNAPSHOT"

repositories {
	maven {
		name = "Devan-Kerman/Devan-Repo"
		url = uri("https://raw.githubusercontent.com/Devan-Kerman/Devan-Repo/master/")
	}

	maven {
		name = "Ladysnake Libs"
		url = uri("https://dl.bintray.com/ladysnake/libs")
	}

	maven {
		name = "Jitpack"
		url = uri("https://jitpack.io")
	}

	maven {
		name = "SschrsRepo"
		url = uri("https://maven.concern.i.ng/")
	}

	maven {
		name = "CurseMaven"
		url = uri("https://www.cursemaven.com/")
	}
}

dependencies {
	minecraft("net.minecraft", "minecraft", "21w06a")
	mappings("net.fabricmc", "yarn", "21w06a+build.7", classifier = "v2")

	modImplementation("net.fabricmc", "fabric-loader", "0.11.0")
	modImplementation("net.fabricmc.fabric-api", "fabric-api", "0.30.2+1.17")

	include(modImplementation("net.devtech", "arrp", "0.3.2"))
	include(modImplementation("software.bernie.geckolib", "fabric-1.16.4-geckolib", "3.0.3", classifier = "dev"))
	include(modImplementation("io.github.onyxstudios.Cardinal-Components-API", "cardinal-components-base", "3.0.0-nightly.20w48a"))
	include(modImplementation("io.github.onyxstudios.Cardinal-Components-API", "cardinal-components-entity", "3.0.0-nightly.20w48a"))

	implementation("com.github.thecodewarrior", "BinarySMD", "-SNAPSHOT")
}

base {
	archivesBaseName = "OpenPixelmon"
}

java {
	sourceCompatibility = JavaVersion.VERSION_1_8
	targetCompatibility = JavaVersion.VERSION_1_8
}

minecraft {
	accessWidener = file("src/main/resources/pixelmon.aw")
}

tasks.withType<JavaCompile> {
	options.encoding = "UTF-8"

	if (JavaVersion.current().isJava9Compatible) {
		options.release.set(8)
	} else {
		sourceCompatibility = "8"
		targetCompatibility = "8"
	}
}

tasks.withType<AbstractArchiveTask> {
	from(file("LICENSE"))
}

tasks.processResources {
	filesMatching("fabric.mod.json") {
		expand("version" to project.version)
	}
}

tasks.remapJar {
	doLast {
		input.get().asFile.delete()
	}
}
