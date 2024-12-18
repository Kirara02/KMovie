# Compose Multiplatform Movie App

This application is the Compose Multiplatform Mobile movie app developed by Kirara Bersntein. The primary objective of this project is to enable the concurrent operation of iOS and Android mobile applications through a unified code base, leveraging the emerging framework, Compose Multiplatform. The application encompasses a range of features, including the display of currently screening movies and TV series, the presentation of nearby movie theaters, and the ability to curate a personalized favorites list through integration with the TMBD movie API.

### Screenshots

#### Android

  <div style="display: flex; width: 100%">
  <img src="docs/android_main.png" width="25%"/>
  <img src="docs/android_detail.png" width="25%"/>
  <img src="docs/android_map.png" width="25%"/>
  </div>

#### IOS
  <div style="display: flex; width: 100%">
  <img src="docs/ios_main.png" width="25%"/>
  <img src="docs/ios_detail.png" width="25%"/>
  <img src="docs/ios_map.png" width="25%"/>
  </div>

## Table of Contents

- [Setup Environment](#setup-environment)
- [API Key](#api-key)
- [Architecture](#architecture)
- [Dependencies](#dependencies)
- [Brancing Strategy](#branching-strategy)
- [License](#license)

## Development

### Setup Environment
**[Compose Multiplatform Template](https://github.com/JetBrains/compose-multiplatform-ios-android-template#set-up-the-environment):** You can see how to setup environment here

#### TMDB API Key

To run the application you need to supply an API key from [TMBD](https://developers.themoviedb.org/3/getting-started/introduction). When you get the key please add following variable to your local environment:

`` API_KEY_TMDB = Your API Key ``

How to set an environment variable in [Mac](https://medium.com/@himanshuagarwal1395/setting-up-environment-variables-in-macos-sierra-f5978369b255) / [Windows](https://www.architectryan.com/2018/08/31/how-to-change-environment-variables-on-windows-10/)

#### MAP API Key

-  To view the google maps you need to supply an API key from [Google Cloud Console](https://developers.google.com/maps/documentation/android-sdk/get-api-key)

- Add your API key to `local.properties`.

```
# local.properties
MAPS_API_KEY=<YOUR_KEY>
```

## Architecture

- Single Activity
- MVVM Pattern

**View:** Renders UI and delegates user actions to ViewModel

**ViewModel:** Can have simple UI logic but most of the time just gets the data from UseCase

**UseCase:** Contains all business rules and they written in the manner of single responsibility principle

**Repository:** Single source of data. Responsible to get data from one or more data sources

## Tech Stack

### Dependencies

- **[Voyager](https://github.com/adrielcafe/voyager) :** Consistent navigation between views
- **[Koin](https://insert-koin.io/docs/setup/koin) :** Dependency injector
- **[Coroutines](https://github.com/Kotlin/kotlinx.coroutines) :** Asynchronous programming
- **[Ktor](https://ktor.io/docs/welcome.html) :** Type safe HTTP client
- **[Kotlinx](https://github.com/Kotlin/kotlinx.serialization) :** JSON serializer/deserializer
- **[Seiko](https://github.com/qdsfdhvh/compose-imageloader) :** Image loading and caching
- **[Kvault](https://github.com/Liftric/KVault/tree/master) :** Secure key-value storage

## Branching Strategy

Gitflow is a legacy Git workflow that was originally a disruptive and novel strategy for managing Git branches. Gitflow has fallen in popularity in favor of trunk-based workflows, which are now considered best practices for modern continuous software development and DevOps practices. Gitflow also can be challenging to use with CI/CD.

| Branch      | Description                                                                                                                                                                                                                                                                             |
| ----------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **Main**    | In the Git flow workflow, the main branch is used to store code that is release-ready and ready for production.                                                                                                                                                                         |
| **Develop** | The develop branch contains pre-production code with recently built features that are currently being tested. It is established at the beginning of a project and maintained during the development process.                                                                            |
| **Feature** | You will create a feature branch off the develop branch while working on a new feature, and once it has been finished and carefully reviewed, you will merge your changes into the develop branch.                                                                                      |
| **Hotfix**  | The hotfix branch is utilized in the Git pipeline to swiftly address required changes in your main branch. Your main branch should serve as the base for the hotfix branch, and it should be merged back into both the main and develop branches.                                       |
| **Release** | The release branch should be used when preparing new production releases. Typically, the work being performed on release branches concerns finishing touches and minor bugs specific to releasing new code, with code that should be addressed separately from the main develop branch. |

- Branch names should start with feature, hotfix or release according to purpose of the branch then should continue with ticket ID. see example: feature/SASU-1234_some-issue
- Pull requests should refer to specific issue with ticketid. see example: [SASU-1234] - New feature
- Merge strategy: Rebase and Merge is preffered for maintaining a linear project history.
