# MapLibre Scale Bar Plugin

The scale bar plugin adds a responsive scale bar on top of the map. The scale bar gives a visual indication of how far various map features are from one another at a certain zoom level. The scale bar can be customized with options such as the text color/size, referesh interval, margins, and border width.

This plugin is currently unmaintained.

<!--![ezgif com-resize (2)](https://user-images.githubusercontent.com/8577318/57837052-89416280-77f4-11e9-9d97-f164737acd46.gif) -->

## Getting Started

To use the scale bar plugin, you include it in your `build.gradle` file.

```
// In the root build.gradle file
repositories {
    mavenCentral()
}

...

// In the app build.gradle file
dependencies {
    implementation 'com.maplibre.maplibresdk:maplibre-android-plugin-scalebar-v9:0.5.0'
}
```

The scale bar plugin is published to Maven Central and nightly SNAPSHOTs are available on Sonatype:

```
// In the root build.gradle file
repositories {
    mavenCentral()
    maven { url "http://oss.sonatype.org/content/repositories/snapshots/" }
}

...

// In the app build.gradle file
dependencies {
    implementation 'com.maplibre.maplibresdk:maplibre-android-plugin-scalebar-v9:0.6.0-SNAPSHOT'
}
```

## Scale bar plugin examples

- [In this repo's test app](https://github.com/maplibre/maplibre-plugins-android/blob/main/app/src/main/java/org/maplibre/android/plugins/testapp/activity/scalebar/ScalebarActivity.kt)

## Help and Usage

This repository includes an app that shows how to use each plugins in this repository. [Check out its code](https://github.com/maplibre/maplibre-plugins-android/tree/main/app/src/main/java/org/maplibre/android/plugins/testapp/activity) for ready-to-use snippets.

Plugins are easy to use. A plugin is simply a library module built on top of the MapLibre Maps SDK for Android. Currently, we are not requiring plugins to register themselves or to implement any specific interfaces so that they're simple to consume.

We'd love to [hear your feedback](https://github.com/maplibre/maplibre-plugins-android/issues) as we build more plugins and learn how you use them.

## Why Plugins

Splitting specific functionality into plugins makes our Maps SDK lighter and nimble for you to use, and it also lets us iterate faster. We can release plugins more often than the SDK, which requires a slower pace due to its larger codebase.

The MapLibre Android team creates plugins but this plugins repository is an open-source project similar to the various MapLibre SDKs for Android.
Plugins' lightweight nature makes them much easier for you and anyone else to contribute rather than trying to add the same feature to the more robust Maps SDK. The MapLibre team can also more easily accept contributed plugins and keep the plugin list growing.

## Contributing

We welcome contributions to this plugin repository!

If you're interested in building and sharing your own plugin, please read [the contribution guide](https://github.com/maplibre/maplibre-plugins-android/blob/main/CONTRIBUTING.md) to learn how to get started.
