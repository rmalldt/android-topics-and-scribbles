package i_concurrency.coroutines.callbacks

import java.lang.RuntimeException
import java.lang.StringBuilder

fun main() {
    // Main Thread
    println("Starting")

    // Run the requests in new thread
    Thread {
        // Callback 1, get versions, do something with it.
        requestVersions { versions ->
            val latestVersion: AndroidVersion = versions.last()
            println(versions.last())

            // Callback 2, get version features, do something with it
            requestFeatures(latestVersion.apiLevel) { versionFeatures ->
                val sb = StringBuilder("Api: ${latestVersion.apiLevel}, Name: ${latestVersion.name}\n" )
                versionFeatures.features.forEach {
                    sb.append(it)
                    sb.append('\n')
                }
                println(sb.toString())
            }
        }
    }.start()
}

fun requestVersions(callback: (List<AndroidVersion>) -> Unit) {
    println("Processing request for versions")
    val versions = versions     // simulate server response
    Thread.sleep(2000)    // simulate network delay
    callback(versions)          // client callback
}

fun requestFeatures(apiLevel: Int, callback: (VersionFeatures) -> Unit) {
    println("Processing request for version features")
    val versionFeature = when (apiLevel) {  // simulate server response
        27 -> features[0]
        28 -> features[1]
        29 -> features[2]
        else -> throw RuntimeException("Invalid version")
    }
    Thread.sleep(1000)  // simulate network delay
    callback(versionFeature)  // client callback
}

data class AndroidVersion(val apiLevel: Int, val name: String)
val oreo = AndroidVersion(27, "Oreo")
val pie = AndroidVersion(28, "Pie")
val android10 = AndroidVersion(29, "Android 10")
val versions = listOf(oreo, pie, android10)

data class VersionFeatures(val androidVersion: AndroidVersion, val features: List<String>)
val featuresOfOreo = listOf(
    "Neural networks API.",
    "Shared memory API.",
    "WallpaperColors API.")

val featuresOfPie = listOf(
    "New user interface for the quick settings menu.",
    "The clock has moved to the left of the notification bar.",
    "The \"dock\" now has a semi-transparent background.")

val featuresOfAndroid10 = listOf(
    "New permissions to access location in background and to access photo, video and audio files",
    "Background apps can no longer jump into the foreground",
    "Limited access to non-resettable device identifiers")

val features = listOf(
    VersionFeatures(oreo, featuresOfOreo),
    VersionFeatures(pie, featuresOfPie),
    VersionFeatures(android10, featuresOfAndroid10),
)
