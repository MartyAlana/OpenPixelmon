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
		name = "SschrsRepo"
		url = uri("https://maven.concern.i.ng/")
	}

	maven {
		name = "CurseMaven"
		url = uri("https://www.cursemaven.com/")
	}
}

dependencies {
	minecraft("net.minecraft", "minecraft", "20w51a")
	mappings("net.fabricmc", "yarn", "20w51a+build.29", classifier = "v2")

	modImplementation("net.fabricmc", "fabric-loader", "0.11.0")
	modImplementation("net.fabricmc.fabric-api", "fabric-api", "0.29.3+1.17")

	include(modImplementation("net.devtech", "arrp", "0.3.2"))
	include(modImplementation("software.bernie.geckolib", "fabric-1.16.4-geckolib", "3.0.3", classifier = "dev"))
	include(modImplementation("io.github.onyxstudios.Cardinal-Components-API", "cardinal-components-base", "3.0.0-nightly.20w48a"))
	include(modImplementation("io.github.onyxstudios.Cardinal-Components-API", "cardinal-components-entity", "3.0.0-nightly.20w48a"))
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
