import io.gitlab.arturbosch.detekt.Detekt
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType.HTML

// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed

plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dagger.hilt) apply false
} // Needed to make the Suppress annotation work for the plugins block

allprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        buildUponDefaultConfig = true
        config.setFrom("$rootDir/config/detekt/detekt.yml")
        baseline = file("detekt-baseline.xml")
        allRules = false
        ignoreFailures = false
        parallel = true
        tasks.withType<Detekt>().configureEach {
            reports {
                html.required.set(true)
                xml.required.set(true)
            }
        }
    }

    ktlint {
        android.set(true)
        outputColorName.set("GREEN")
        outputColorName.set("BLUE")
        filter {
            enableExperimentalRules.set(true)
            exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated") }
            include("**/kotlin/**")
        }
        reporters {
            reporter(HTML)
        }
    }
}
