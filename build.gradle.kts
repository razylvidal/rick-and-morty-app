
import com.android.build.gradle.BaseExtension
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
}

val reportMerge by tasks.registering(ReportMergeTask::class) {
    output.set(file("${rootDir}/build/reports/merge.xml"))
}

subprojects {
    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("io.gitlab.arturbosch.detekt")
    }

    configure<KtlintExtension> {
        debug.set(true)
        android.set(true)
        outputColorName.set("RED")
        reporters {
            reporter(ReporterType.HTML)
        }
    }

    configure<DetektExtension> {
        config.setFrom(files("$rootDir/config/detekt/detekt.yml"))
        baseline = file("detekt-baseline.xml")
        parallel = true
        ignoredBuildTypes = listOf("release")
    }

    reportMerge {
        input.from(tasks.withType<Detekt>().map { it.xmlReportFile })
    }

    tasks.withType<Detekt>().configureEach {
        finalizedBy(reportMerge)
        reports {
            html.required.set(true)
            xml.required.set(true)
        }
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    project.afterEvaluate {
        if (project.hasProperty("android")) {
            configure<BaseExtension> {
                compileSdkVersion(34)
                defaultConfig.apply {
                    minSdk = 24
                    targetSdk = 34
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    buildFeatures.viewBinding = true
                }

                tasks.withType<KotlinCompile>().configureEach{
                    kotlinOptions {
                        jvmTarget = JavaVersion.VERSION_17.toString()
                    }
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_17
                    targetCompatibility = JavaVersion.VERSION_17
                }

                packagingOptions {
                    jniLibs {
                        excludes.addAll(
                            listOf(
                                "DebugProbesKt.bin",
                                "META-INF/proguard/coroutines.pro",
                                "META-INF/DEPENDENCIES",
                                "META-INF/LICENSE",
                                "META-INF/LICENSE.txt",
                                "META-INF/license.txt",
                                "META-INF/NOTICE",
                                "META-INF/NOTICE.txt",
                                "META-INF/notice.txt",
                                "META-INF/ASL2.0",
                                "META-INF/INDEX.LIST",
                                "META-INF/*.kotlin_module",
                                "project.properties",
                                "**/kotlin/**",
                                "**/*.txt",
                                "**/*.xml",
                                "**/*.properties"
                            )
                        )
                    }
                }

                testOptions {
                    unitTests {
                        isIncludeAndroidResources = true
                        isReturnDefaultValues = true
                    }
                }

                tasks.withType<Test>().configureEach{
                    maxParallelForks = Runtime.getRuntime().availableProcessors().div(2).takeIf { it > 0 } ?: 1
                }
            }
        }
    }
}



