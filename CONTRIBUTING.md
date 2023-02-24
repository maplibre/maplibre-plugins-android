# Contributing

We welcome contributions to this plugin repository. Please be aware that all plugins, except Annotations, are not actively maintained. If you are interested in becoming a maintainer for a plugin feel free to submit a pull request with a short introduction and your name and GitHub handle in the respective README.

If you're interested in building and sharing your own plugin, please follow these steps:

- [Open a ticket](https://github.com/maplibre/maplibre-plugins-android/issues/new) to kick off a conversation. It's a good idea to explain your plans before you push any code to make sure noone else is working on something similar and to discuss the best approaches to tackle your particular idea.

- Create a new branch that will contain the code for your plugin.

- Create a new Android library module inside the `plugins` project with Android Studio. Each plugin is a separate library that depends on the MapLibre Android SDK, and potentially on other third-party libraries. Besides the code for the plugin, make sure you include:

  - Tests.
  - Javadoc that documents the plugin usage and purpose in detail.

- Finally, once you're ready to share your code, list your plugin in this file and then open a PR for other contributors to review.