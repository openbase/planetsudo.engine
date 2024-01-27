import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.util.*

/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    `java-library`
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
}

group = "org.openbase"
description =
    "PlanetSudo is a reactive multi-agent simulation game. This package contains the game engine of PlanetSudo."

val releaseVersion = !version.toString().endsWith("-SNAPSHOT")

kotlin {
    jvmToolchain(17)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = sourceCompatibility
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
    mavenCentral()
}

dependencies {
    api(libs.org.openbase.jul.processing.json)
    api(libs.org.openbase.jul.visual.swing)
    api(libs.org.openbase.jul.schedule)
    api(libs.org.openbase.jul.audio)
    api(libs.org.openbase.jul.`interface`)
    api(libs.org.jdesktop.beansbinding)
    api(libs.tomcat.tomcat.util)
    api(libs.commons.io.commons.io)
    testImplementation(libs.junit.junit)
    implementation(kotlin("stdlib-jdk8"))
}



nexusPublishing {
    repositories {
        sonatype {
            username.set(findProperty("MAVEN_CENTRAL_USERNAME")?.let { it as String? })
            password.set(findProperty("MAVEN_CENTRAL_TOKEN")?.let { it as String? })
        }
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set(rootProject.name)
                description.set(rootProject.description)
                url.set("https://github.com/openbase/planetsudo")
                inceptionYear.set("2009")
                organization {
                    name.set("openbase.org")
                    url.set("https://openbase.org")
                }
                licenses {
                    license {
                        name.set("GPLv3")
                        url.set("https://www.gnu.org/licenses/gpl.html")
                    }
                }
                developers {
                    developer {
                        id.set("DivineThreepwood")
                        name.set("Marian Pohling")
                        email.set("divine@openbase.org")
                        url.set("https://github.com/DivineThreepwood")
                        organizationUrl.set("https://github.com/openbase")
                        organization.set("openbase.org")
                        roles.set(listOf("architect", "developer"))
                        timezone.set("+1")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/openbase/planetsudo.engine.git")
                    developerConnection.set("scm:git:https://github.com/openbase/planetsudo.engine.git")
                    url.set("https://github.com/openbase/planetsudo.engine.git")
                }
            }
        }
    }
}

signing {
    val privateKey = findProperty("OPENBASE_GPG_PRIVATE_KEY")
        ?.let { it as String? }
        ?.let { Base64.getDecoder().decode(it) }
        ?.let { String(it) }
        ?: run {
            // Signing skipped because of missing private key.
            return@signing
        }

    val passphrase = findProperty("OPENBASE_GPG_PRIVATE_KEY_PASSPHRASE")
        ?.let { it as String? }
        ?.let { Base64.getDecoder().decode(it) }
        ?.let { String(it) }
        ?: ""

    useInMemoryPgpKeys(
        privateKey,
        passphrase
    )
    sign(publishing.publications["mavenJava"])
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}


ktlint {
    disabledRules.set(setOf("no-wildcard-imports"))
    filter {
        exclude { entry -> entry.file.toString().contains("generated") }
    }
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.HTML)
        reporter(ReporterType.CHECKSTYLE)
    }
}
