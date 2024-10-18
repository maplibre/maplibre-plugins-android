# MapLibre MarkerView plugin

<!-- ![markerview-plugin](https://user-images.githubusercontent.com/2151639/45137542-69f42f00-b1a9-11e8-854b-3335a5504337.gif) -->

This plugin is currently unmaintained.

## Getting Started

<!-- [More documentation about the plugin can be found here](https://www.mapbox.com/android-docs/plugins/overview/markerview/). -->

Please be aware that Android views are the less performant and less customizable option for adding icons to a map. The MapLibre Maps SDK's SymbolLayer is the recommended way to add icons.

To use the MarkerView plugin, you include its dependency in your `build.gradle` file.

In the root `build.gradle` file:

```groovy
repositories {
    mavenCentral()
}

```

Add [the latest version](https://central.sonatype.com/artifact/org.maplibre.gl/android-plugin-markerview-v9/versions) as a dependency to your project.

In the app-level `build.gradle` file:

```groovy
dependencies {
    implementation 'org.maplibre.gl:android-plugin-markerview-v9:3.0.2'
}
```

```kotlin
dependencies {
    implementation("org.maplibre.gl:android-plugin-markerview-v9:3.0.2")
}
```

## MarkerView plugin examples

- [In this repo's test app](https://github.com/maplibre/maplibre-plugins-android/blob/main/app/src/main/java/org/maplibre/android/plugins/testapp/activity/markerview/MarkerViewActivity.kt)

## Help and Usage

This repository includes an app that shows how to use each plugin in this repository. [Check out its code](https://github.com/maplibre/maplibre-plugins-android/tree/main/app/src/main/java/org/maplibre/android/plugins/testapp/activity) for ready-to-use snippets.

We'd love to [hear your feedback](https://github.com/maplibre/maplibre-plugins-android/issues) as we build more plugins and learn how you use them.

## Why Plugins

Splitting specific functionality into plugins makes our Map SDK lighter and nimble for you to use, and it also lets us iterate faster. We can release plugins more often than the SDK, which requires a slower pace due to its larger codebase.

The MapLibre Android team creates plugins but this plugins repository is an open-source project similar to the various MapLibre SDKs for Android.
Plugins' lightweight nature makes them much easier for you and anyone else to contribute rather than trying to add the same feature to the more robust Map SDK. The MapLibre team can also more easily accept contributed plugins and keep the plugin list growing.

## Contributing

We welcome contributions to this plugin repository!

If you're interested in building and sharing your own plugin, please read [the contribution guide](https://github.com/maplibre/maplibre-plugins-android/blob/main/CONTRIBUTING.md) to learn how to get started.
