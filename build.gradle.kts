import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType
import org.jlleitschuh.gradle.ktlint.tasks.GenerateReportsTask

plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
}

subprojects {
    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("io.gitlab.arturbosch.detekt")
    }

    configure<KtlintExtension> {
        verbose.set(true)
        android.set(true)
        outputColorName.set("RED")
        outputToConsole.set(true)
        reporters {
            reporter(ReporterType.HTML)
            reporter(ReporterType.CHECKSTYLE)
        }
//        filter {
//            enableExperimentalRules.set(false)
//            exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated") }
//            include("**/kotlin/**")
//        }
    }

    tasks.withType<GenerateReportsTask> {
        reportsOutputDirectory.set(
            project.layout.buildDirectory.dir("reports/ktlint")
        )
    }

    configure<DetektExtension> {
        buildUponDefaultConfig = true
        config.setFrom("$rootDir/config/detekt/detekt.yml")
        baseline = file("detekt-baseline.xml")
        allRules = false
        ignoreFailures = false
        parallel = true
    }

    tasks.withType<Detekt>().configureEach {
        reports {
            html.required.set(true)
            xml.required.set(true)
            md.required.set(false)
            sarif.required.set(false)
            txt.required.set(false)
        }
    }
}
