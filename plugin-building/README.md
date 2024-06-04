# MapLibre Building Plugin

<!-- ![buildings-plugin](https://user-images.githubusercontent.com/4394910/28844435-71442d04-76b9-11e7-8866-ee6a94306353.gif) -->

This plugin is currently unmaintained.

## Getting Started

<!-- [More documentation about the plugin can be found here](https://www.mapbox.com/android-docs/plugins/overview/building/) -->

To use the building plugin you include it in your `build.gradle` file.

```
// In the root build.gradle file
repositories {
    mavenCentral()
}

...

// In the app build.gradle file
dependencies {
    implementation 'com.maplibre.maplibresdk:maplibre-android-plugin-building-v9:0.7.0'
}
```

The building plugins is published to Maven Central and nightly SNAPSHOTs are available on Sonatype:

```
// In the root build.gradle file
repositories {
    mavenCentral()
    maven { url "http://oss.sonatype.org/content/repositories/snapshots/" }
}

...

// In the app build.gradle file
dependencies {
    implementation 'com.maplibre.maplibresdk:maplibre-android-plugin-building-v9:0.8.0-SNAPSHOT'
}
```

## Building plugin examples

- [In this repo's test app](https://github.com/maplibre/maplibre-plugins-android/blob/master/app/src/main/java/com/mapbox/mapboxsdk/plugins/testapp/activity/building/BuildingActivity.kt)

## Help and Usage

This repository includes an app that shows how to use each plugins in this repository. [Check out its code](https://github.com/maplibre/maplibre-plugins-android/tree/main/app/src/main/java/org/maplibre/android/plugins/testapp/activity) for ready-to-use snippets.

We'd love to [hear your feedback](https://github.com/maplibre/maplibre-plugins-android/issues) as we build more plugins and learn how you use them.

## Why Plugins

Splitting specific functionality into plugins makes our Map SDK lighter and nimble for you to use, and it also lets us iterate faster. We can release plugins more often than the SDK, which requires a slower pace due to its larger codebase.

The MapLibre Android team creates plugins but this plugins repository is an open-source project similar to the various MapLibre SDKs for Android.
Plugins' lightweight nature makes them much easier for you and anyone else to contribute rather than trying to add the same feature to the more robust Map SDK. The MapLibre team can also more easily accept contributed plugins and keep the plugin list growing.

## Contributing

We welcome contributions to this plugin repository!

If you're interested in building and sharing your own plugin, please read [the contribution guide](https://github.com/maplibre/maplibre-plugins-android/blob/main/CONTRIBUTING.md) to learn how to get started.
