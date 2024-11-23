
import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.buildKonfig)
    alias(libs.plugins.mapsSecrets)
}

buildkonfig {
    packageName = "id.kirara.kmovie"

    defaultConfigs {
        buildConfigField(
            FieldSpec.Type.STRING,
            "API_KEY_TMDB",
            rootProject.requireStringProperty("API_KEY_TMDB"),
            const = true
        )

        buildConfigField(
            FieldSpec.Type.STRING,
            "BASE_URL",
            rootProject.requireStringProperty("BASE_URL"),
            const = true
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            "IMAGE_BASE_URL",
            rootProject.requireStringProperty("IMAGE_BASE_URL"),
            const = true
        )
        buildConfigField(
            FieldSpec.Type.STRING,
            "REGISTER_URL",
            rootProject.requireStringProperty("REGISTER_URL"),
            const = true
        )

        buildConfigField(
            FieldSpec.Type.STRING,
            "RESET_PASSWORD_URL",
            rootProject.requireStringProperty("RESET_PASSWORD_URL"),
            const = true
        )
    }
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {

        androidMain.dependencies {

            api(libs.ktor.client.android)

            // Koin
            api(libs.koin.android)
            api(libs.koin.core)

            api(libs.androidx.core)
            api(libs.androidx.appcompat)
            api(libs.androidx.activity.compose)

            api(libs.androidx.compose.ui.util)
            api(libs.androidx.compose.ui.tooling)
            api(libs.androidx.compose.ui.preview)

            api(libs.maps.compose)

            //Location
            api(libs.play.services.location)
            api(libs.play.services.maps)
        }

        commonMain.dependencies {

            // Compose
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material)
            api(compose.material3)
            api(compose.animation)
            api(compose.materialIconsExtended)
            api(compose.components.resources)

            implementation(libs.logger)

            // Coroutines
            api(libs.kotlinx.coroutines.core)

            // KotlinX Serialization Json
            api(libs.kotlinx.serialization.json)

            // Ktor
            api(libs.ktor.core)
            api(libs.ktor.json)
            api(libs.ktor.contentNegotiation)
            api(libs.ktor.logging)
            api(libs.ktor.logging.logback)

            // Koin
            api(libs.koin.core)
            api(libs.koin.test)
            api(libs.koin.compose)

            //Navigation
            api(libs.voyager.navigator)
            api(libs.voyager.koin)
            api(libs.voyager.tabs)
            api(libs.voyager.transitions)

            //Image loader
            api(libs.image.loader)

            //KVault
            api(libs.settings)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

    }
}

android {
    namespace = "id.kirara.kmovie"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "id.kirara.kmovie"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file(requireStringProperty("SIGNING_STORE_FILE"))
            storePassword = requireStringProperty("SIGNING_STORE_PASSWORD")
            keyAlias = requireStringProperty("SIGNING_KEY_ALIAS")
            keyPassword = requireStringProperty("SIGNING_KEY_PASSWORD")
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    dependencies {
        coreLibraryDesugaring(libs.desugar.jdk.libs)
    }
    buildFeatures {
        buildConfig = true
    }
}

secrets {
    defaultPropertiesFileName = "default.local.properties"
    propertiesFileName = "local.properties"
}

fun Project.requireStringProperty(key: String): String {
    return (properties[key] as? String)!!
}