import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import java.util.*

/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    `java-library`
    `maven-publish`
    signing
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
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

group = "org.openbase"
version = "4.0.0-SNAPSHOT"
description =
    "PlanetSudo is a reactive multi-agent simulation game. This package contains the game engine of PlanetSudo."

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
                description.set("Java Utility Lib")
                url.set("https://github.com/openbase/jul/wiki")
                inceptionYear.set("2015")
                organization {
                    name.set("openbase.org")
                    url.set("https://openbase.org")
                }
                licenses {
                    license {
                        name.set("LGPLv3")
                        url.set("https://www.gnu.org/licenses/lgpl.html")
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
                    connection.set("scm:git:https://github.com/openbase/planetsudo.git")
                    developerConnection.set("scm:git:https://github.com/openbase/planetsudo.git")
                    url.set("https://github.com/openbase/planetsudo.git")
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
kotlin {
    jvmToolchain(17)
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
