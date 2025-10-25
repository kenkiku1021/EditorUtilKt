import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
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
            implementation("com.twelvemonkeys.imageio:imageio-core:3.12.0")
            implementation("com.twelvemonkeys.imageio:imageio-jpeg:3.12.0")
            implementation("com.twelvemonkeys.imageio:imageio-tiff:3.12.0")
            implementation("com.twelvemonkeys.imageio:imageio-psd:3.12.0")
            implementation("com.twelvemonkeys.imageio:imageio-webp:3.12.0")
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
            implementation("com.russhwolf:multiplatform-settings-no-arg:1.3.0")
        }
    }
}

compose.desktop {
    application {
        mainClass = "jp.co.hamajima.editorutil.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Exe, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "EditorUtil"
            packageVersion = "1.0.1"

            windows {
                menuGroup = "EditorUtil"
                perUserInstall = true
                upgradeUuid = "ffa0583d-1160-45ba-ac21-2bfd4811bfc6"
            }
        }
    }
}
