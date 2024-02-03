plugins {
    id("com.android.application")
}

android {
    namespace = "spr2024.cs2340.group9.studysync"
    compileSdk = 34

    defaultConfig {
        applicationId = "spr2024.cs2340.group9.studysync"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

dependencies {
    implementation("io.reactivex.rxjava3:rxjava:3.1.8")
}

dependencies {
    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // optional - RxJava3 support for Room
    implementation("androidx.room:room-rxjava3:$room_version")

    // optional - Test helpers
    testImplementation("androidx.room:room-testing:$room_version")

    // Material Design
    implementation("com.google.android.material:material:1.2.1")
    // Swiping Decorator for editing and deleting items
    implementation("it.xabaras.android:recyclerview-swipedecorator:1.4")

    testImplementation("org.robolectric:robolectric:4.11.1")
}

dependencies {
    val work_version = "2.9.0"

    // (Java only)
    implementation("androidx.work:work-runtime:$work_version")

    // Notifications
    implementation("androidx.core:core:1.12.0")
}