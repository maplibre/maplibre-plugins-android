# MapLibre Localization Plugin

<!-- ![localization plugin](https://user-images.githubusercontent.com/4394910/35408667-d7689174-01c4-11e8-8bdb-e580df77d90b.gif) -->

This plugin is currently unmaintained.

## Getting Started

<!-- [More documentation about the plugin can be found here](https://www.mapbox.com/android-docs/plugins/overview/localization/) -->

To use the localization plugin, you include it in your `build.gradle` file.

```gradle
// In the root build.gradle file
repositories {
    mavenCentral()
    google()
}

...

// In the app build.gradle file
dependencies {
    implementation 'com.maplibre.maplibresdk:maplibre-android-plugin-localization-v9:0.12.0'
}
```

The location layer plugin is published to Maven Central and nightly SNAPSHOTs are available on Sonatype:

```gradle
// In the root build.gradle file
repositories {
    mavenCentral()
    google()
    maven { url "http://oss.sonatype.org/content/repositories/snapshots/" }
}

...

// In the app build.gradle file
dependencies {
    implementation 'com.maplibre.maplibresdk:maplibre-android-plugin-localization-v9:0.13.0-SNAPSHOT'
}
```

## Localization examples

- [In this repo's test app](https://github.com/maplibre/maplibre-plugins-android/blob/main/app/src/main/java/org/maplibre/android/plugins/testapp/activity/localization/LocalizationActivity.kt)

## Help and Usage

This repository includes an app that shows how to use each plugins in this repository. [Check out its code](https://github.com/maplibre/maplibre-plugins-android/tree/main/app/src/main/java/org/maplibre/android/plugins/testapp/activity) for ready-to-use snippets.

We'd love to [hear your feedback](https://github.com/maplibre/maplibre-plugins-android/issues) as we build more plugins and learn how you use them.

## Why plugins

Splitting specific functionality into plugins makes our Map SDK lighter and nimble for you to use, and it also lets us iterate faster. We can release plugins more often than the SDK, which requires a slower pace due to its larger codebase.

The MapLibre Android team creates plugins but this plugins repository is an open-source project similar to the various MapLibre SDKs for Android.
Plugins' lightweight nature makes them much easier for you and anyone else to contribute rather than trying to add the same feature to the more robust Map SDK. The MapLibre team can also more easily accept contributed plugins and keep the plugin list growing.

## Contributing

We welcome contributions to this plugin repository!

If you're interested in building and sharing your own plugin, please read [the contribution guide](https://github.com/maplibre/maplibre-plugins-android/blob/main/CONTRIBUTING.md) to learn how to get started.
