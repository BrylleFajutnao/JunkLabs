plugins {
    id("com.android.application") version "8.7.3" apply false
    id("org.jetbrains.kotlin.android") version "2.0.21" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.21" apply false  // Correct plugin ID
    id("com.google.gms.google-services") version "4.4.4" apply false
}

buildscript {
    dependencies {
        classpath("com.google.gms:google-services:4.4.0")  // Should be here
    }
}