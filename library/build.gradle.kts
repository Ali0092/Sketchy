import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.maven.publish)
}

group = "io.github.ali0092"
// CI overrides this via -PlibraryVersion when auto-tagging a release; the
// literal here is only the fallback for local builds and the first release.
version = (findProperty("libraryVersion") as String?) ?: "0.1.0"

kotlin {
    jvmToolchain(21)

    androidLibrary {
        namespace = "com.sketchy.library"
        compileSdk = 37
        minSdk = 26
    }

    jvm()

    // No iosX64: Compose Multiplatform no longer ships artifacts for the Intel
    // simulator. Apple-silicon Macs use iosSimulatorArm64.
    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { target ->
        target.binaries.framework {
            baseName = "Sketchy"
            isStatic = true
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            // `api`, not `implementation`: Modifier, TextStyle and Dp appear in the
            // public signatures of SketchyIllustration/SketchyEmptyState, so consumers
            // need these on their compile classpath.
            api(compose.runtime)
            api(compose.foundation)
            api(compose.ui)
        }
    }
}

mavenPublishing {
    publishToMavenCentral(automaticRelease = true)
    signAllPublications()

    coordinates(group.toString(), "sketchy", version.toString())

    pom {
        name.set("Sketchy")
        description.set("Hand-drawn, animated illustrations and empty states for Compose Multiplatform.")
        url.set("https://github.com/Ali0092/Sketchy")

        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("repo")
            }
        }

        developers {
            developer {
                id.set("Ali0092")
                name.set("Muhammad Ali")
                url.set("https://github.com/Ali0092")
            }
        }

        scm {
            url.set("https://github.com/Ali0092/Sketchy")
            connection.set("scm:git:git://github.com/Ali0092/Sketchy.git")
            developerConnection.set("scm:git:ssh://git@github.com/Ali0092/Sketchy.git")
        }
    }
}
