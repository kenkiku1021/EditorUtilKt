import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

kotlin {
    jvm()

    sourceSets {

        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
            // Skiko AWT を明示的に追加して、BufferedImage -> ImageBitmap の拡張をコンパイル時に利用可能にする
            implementation("org.jetbrains.skiko:skiko-awt:0.9.22.2")
            // プラットフォーム固有のランタイムも追加（Windows x64）
            implementation("org.jetbrains.skiko:skiko-awt-runtime-windows-x64:0.9.22.2")
        }
    }
}

compose.desktop {
    application {
        mainClass = "jp.co.hamajima.editorutil.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "EditorUtil"
            packageVersion = "1.0.2"

            windows {
                menuGroup = "EditorUtil"
                perUserInstall = true
                upgradeUuid = "ffa0583d-1160-45ba-ac21-2bfd4811bfc6"
            }
        }
    }
}
