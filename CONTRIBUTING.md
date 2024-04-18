# Contributing

We welcome contributions to this plugin repository. If you're interested in building and sharing your own, please follow these steps:

- [Open a ticket](https://github.com/maplibre/maplibre-plugins-android/issues/new) to kick off a conversation. It's a good idea to explain your plans before you push any code to make sure noone else is working on something similar and to discuss the best approaches to tackle your particular idea.

- Create a new branch that will contain the code for your plugin.

- Create a new Android library module inside the `plugins` project with Android Studio. Each plugin is a separate library that depends on the Mapbox Android SDK, and potentially on other third-party libraries. Besides the code for the plugin, make sure you include:

  - Tests.
  - Javadoc that documents the plugin usage and purpose in detail.

- Create a new activity inside the demo app that showcases how to use your plugin. As important as having a working plugin is to show how to use it so that anyone can include it in their projects easily.

- Finally, once you're ready to share your code, list your plugin in this file and then open a PR for the `@maplibre/android` team to review.
