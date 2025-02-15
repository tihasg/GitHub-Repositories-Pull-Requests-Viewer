plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.tihasg.br.repositories"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    // AndroidX e Material Design
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)

    // Dagger2 para IoC
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    // RxJava
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.rxkotlin)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.retrofit.rxjava2)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Glide
    implementation(libs.glide)

    // Testes
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.robolectric)
}