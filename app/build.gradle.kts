plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.dokka)

}

android {
    namespace = "es.jualas.peliculas"
    compileSdk = 35

    defaultConfig {
        applicationId = "es.jualas.peliculas"
        minSdk = 34
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {


    // Core y UI
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // SplashScreen API
    implementation(libs.androidx.core.splashscreen)

    // Glide para carga de imágenes
    implementation(libs.glide)

    // RecyclerView
    implementation(libs.androidx.recyclerview)

    // Usando referencia a una sola librería
    implementation(libs.androidx.swiperefreshlayout)

    // O usando un bundle completo
    implementation(libs.bundles.androidx.base)

    
    // Navigation Component
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    
    // ViewModel y LiveData
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.runtime.ktx)

    // Identificarse con cuentas de Google
    implementation(libs.play.services.auth)
    
    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.firestore)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

// Configuración de Dokka
tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    outputDirectory.set(file("$projectDir/../docs"))
}

// Configuración específica para formato GFM (GitHub Flavored Markdown)
tasks.named<org.jetbrains.dokka.gradle.DokkaTask>("dokkaGfm") {
    outputDirectory.set(file("$projectDir/../docs/markdown"))
}

// Configuración específica para formato HTML
tasks.named<org.jetbrains.dokka.gradle.DokkaTask>("dokkaHtml") {
    outputDirectory.set(file("$projectDir/../docs/html"))
}