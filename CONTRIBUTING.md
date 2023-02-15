# Contributing

We welcome contributions to this plugin repository. Please be aware that all plugins, except Annotations, is currently unmaintained. If you're interested in building and sharing your own, please follow these steps:

- [Open a ticket](https://github.com/maplibre/maplibre-plugins-android/issues/new) to kick off a conversation. It's a good idea to explain your plans before you push any code to make sure noone else is working on something similar and to discuss the best approaches to tackle your particular idea.

- Create a new branch that will contain the code for your plugin.

- Create a new Android library module inside the `plugins` project with Android Studio. Each plugin is a separate library that depends on the MapLibre Android SDK, and potentially on other third-party libraries. Besides the code for the plugin, make sure you include:

  - Tests.
  - Javadoc that documents the plugin usage and purpose in detail.

- Finally, once you're ready to share your code, list your plugin in this file and then open a PR for other contributors to review.

## Adding or updating a localization

The MapLibre Plugins SDK for Android features several translations contributed through [Transifex](https://www.transifex.com/maplibre/maplibre-plugins-android/). If your language already has a translation, feel free to complete or proofread it. Otherwise, please [request your language](https://www.transifex.com/maplibre/maplibre-plugins-android/) so you can start translating. Note that we’re primarily interested in languages that Android supports as system languages.

Once you’ve finished translating the Android Plugins SDK into a new language in Transifex, open an issue in this repository asking to pull in your translations. You can also pull in the translations yourself:

1. Create a new branch that will contain the new translations.
1. _(First time only.)_ Download the [`tx` command line tool](https://docs.transifex.com/client/installing-the-client) and [configure your .transifexrc](https://docs.transifex.com/client/client-configuration).
1. Run `tx pull -a` to fetch translations from Transifex. You can restrict the operation to just the new language using `tx pull -l xyz`, where _xyz_ is the language code.
1. Commit any new files that were added and open a pull request with your changes.
