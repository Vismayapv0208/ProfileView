# ProfileView Android App

## 🌟 Overview

ProfileView is an Android application that showcases a list of popular people's profiles with endless scrolling. Users can search for profiles, view detailed descriptions and images from MockAPI and create, edit, and delete sample profiles in Room DB. The app utilizes modern Android development practices, including Kotlin Flow, Hilt, MVVM architecture, and the Android Paging library for efficient data handling.

## ✨ Features

*   **Popular Profiles:** Displays a list of popular people's profiles with endless scrolling.
*   **Profile Search:** Users can search for profiles and view detailed descriptions and images.
*   **Profile Management:** Users can create, edit, and delete sample profiles (using RecyclerView).
*   **Kotlin Flow:** Advanced use of Kotlin Flow for asynchronous data streams.
*   **Hilt:** Dependency injection with Hilt.
*   **MVVM Architecture:** Implements the Model-View-ViewModel pattern with Android Architecture Components (Room, LiveData, ViewModel).
*   **Pagination:** Endless scrolling using the Android Paging library for efficient data loading.
*   **Material Design 3:** Modern UI design using Material Design 3 components.
*   **Firebase Analytics:** Tracks user interactions with key events such as profile creation, editing, and deletion.
*   **Firebase Crashlytics:** Monitors and reports app crashes, providing detailed crash reports.
*   **Retrofit:** Third-party library for network requests.
* **Firebase Performance**: Monitor network performance.

## 🛠️ Prerequisites

*   **Android Studio:** Ladybug or a more recent version.
*   **Java JDK:** Version 17 or 11.
*   **Android Device or Emulator:** To run the app.

## 🚀 Setup and Run Instructions

1.  **The Movie Database API:**
    *   Sign up for an account at [The Movie Database (TMDB)](https://www.themoviedb.org/documentation/api).
    *   Retrieve your API key and access token from your TMDB account settings.
2.  **Clone the Project:**
    *   Download or clone the project repository to your local machine.
3.  **Open in Android Studio:**
    *   Open Android Studio and select "Open an Existing Project."
    *   Navigate to the project directory and open it.
4.  **Configure API Key and Access Token:**
    *   Open the `NetworkModule.kt` file located at `com.example.profileview.network.NetworkModule`.
    *   Replace the placeholder values for `API_KEY` and `ACCESS_TOKEN` with your actual TMDB API key and access token.
5.  **Sync Project with Gradle Files:**
    *   Click the "Sync Project with Gradle Files" button in the toolbar (it looks like an elephant with a green arrow).
6.  **Build and Run:**
    *   Connect your Android phone or start an emulator.
    *   Click the "Run" button (green play icon) in Android Studio to build and run the application.

## 📚 Third-Party Library Integration

This project leverages several third-party libraries to enhance functionality and streamline development. Here's a breakdown of each:

*   **AndroidX:**
    *   **Purpose:** Provides a set of libraries that help you build robust, testable, and maintainable apps.
    *   **Integration:** Included as dependencies in the `build.gradle.kts` (Module :app) file.
    *   **Usage:** Used throughout the project for various components like `AppCompatActivity`, `RecyclerView`, `ConstraintLayout`, etc.
*   **Glide:**
    *   **Purpose:** Efficient image loading and caching.
    *   **Integration:** Added as a dependency in `build.gradle.kts` (Module :app).
    *   **Usage:** Used to load images from network URLs into `ImageView` components.
*   **Retrofit 2:**
    *   **Purpose:** Type-safe HTTP client for making network requests.
    *   **Integration:** Added as a dependency in `build.gradle.kts` (Module :app).
    *   **Usage:** Used to communicate with The Movie Database API to fetch profile data.
*   **Gson:**
    *   **Purpose:** Serialization/deserialization of Java objects to/from JSON.
    *   **Integration:** Added as a dependency in `build.gradle.kts` (Module :app).
    *   **Usage:** Used by Retrofit to convert JSON responses into Kotlin data classes.
*   **LiveData:**
    *   **Purpose:** Lifecycle-aware data holder that allows UI components to observe data changes.
    *   **Integration:** Part of the AndroidX libraries.
    *   **Usage:** Used in the ViewModel to hold and expose data to the UI.
*   **ViewModel:**
    *   **Purpose:** Stores and manages UI-related data in a lifecycle-conscious way.
    *   **Integration:** Part of the AndroidX libraries.
    *   **Usage:** Used to separate UI logic from data management and business logic.
*   **Paging:**
    *   **Purpose:** Loads and displays data in pages, enabling endless scrolling.
    *   **Integration:** Added as a dependency in `build.gradle.kts` (Module :app).
    *   **Usage:** Used to fetch and display paginated profile data from the API.
*   **DataBinding:**
    *   **Purpose:** Declaratively bind UI components to data sources.
    *   **Integration:** Enabled in `build.gradle.kts` (Module :app).
    *   **Usage:** Used to simplify UI updates and reduce boilerplate code.
*   **OkHttp:**
    *   **Purpose:** HTTP client that Retrofit uses under the hood.
    *   **Integration:** Included as a dependency of Retrofit.
    *   **Usage:** Handles network requests and responses.
*   **Kotlin Flow:**
    *   **Purpose:** Asynchronous data stream handling.
    *   **Integration:** Part of the Kotlin standard library.
    *   **Usage:** Used for handling asynchronous operations and data streams in the ViewModel and repository.
*   **Hilt:**
    *   **Purpose:** Dependency injection for Android.
    *   **Integration:** Added as dependencies and plugins in `build.gradle.kts` (Project and Module :app).
    *   **Usage:** Used to manage dependencies and inject them into classes.
*   **Firebase:**
    *   **Purpose:** Provides various services, including Analytics, Crashlytics, and Performance Monitoring.
    *   **Integration:** Added as dependencies and plugins in `build.gradle.kts` (Project and Module :app).
    *   **Usage:**
        *   **Firebase Analytics:** Tracks key user interactions, including:
            *   **Profile Created:** When a user creates a new profile.
            *   **Profile Edited:** When a user edits an existing profile.
            *   **Profile Deleted:** When a user deletes a profile.
        *   **Firebase Crashlytics:** Monitors and reports app crashes, providing detailed crash reports to help identify and fix issues.
        * **Firebase Performance**: Monitor network performance.
* **Room**:
    * **Purpose**: Persistence library.
    * **Integration**: Added as dependencies in `build.gradle.kts` (Module :app).
    * **Usage**: Used to store data locally.

## 🎨 Design Decisions

*   **MVVM Architecture:**
    *   **Reason:** Separates UI logic from data and business logic, making the code more testable, maintainable, and scalable.
    *   **Implementation:** ViewModels handle data and business logic, LiveData exposes data to the UI, and the UI observes LiveData for updates.
*   **Kotlin Flow:**
    *   **Reason:** Provides a reactive stream API for handling asynchronous data streams, making it easier to manage complex asynchronous operations.
    *   **Implementation:** Used in the ViewModel and repository to handle network requests and data updates.
*   **Hilt for Dependency Injection:**
    *   **Reason:** Simplifies dependency management and reduces boilerplate code.
    *   **Implementation:** Used to inject dependencies into classes, making the code more modular and testable.
*   **RecyclerView:**
    *   **Reason:** Efficiently displays large lists of data, and it's highly customizable.
    *   **Implementation:** Used to display the list of popular profiles, allowing for smooth scrolling and efficient view recycling. It's also used for managing the list of user-created profiles, enabling users 
            to create, edit, and delete them.
*   **Paging Library:**
    *   **Reason:** Efficiently loads and displays large datasets in pages, improving performance and user experience.
    *   **Implementation:** Used to fetch and display paginated profile data from the API, enabling endless scrolling.
*   **Material Design 3:**
    *   **Reason:** Provides a modern and consistent UI design.
    *   **Implementation:** Used Material Design 3 components throughout the app.
* **Room**:
    * **Reason**: Store data locally.
    * **Implementation**: Used to store data locally.
* **Firebase**:
    * **Reason**: Monitor the app.
    * **Implementation**: Used to monitor the app.

## 📊 Firebase Crashlytics Screenshot
![crash_2](https://github.com/user-attachments/assets/e0eb9bad-a31f-4949-84f4-00d6b391a163)
![crash_1](https://github.com/user-attachments/assets/de2d979b-b99c-414c-a5e5-494ec880df68)

## 📊More Screenshot and screenrecording of App:
https://drive.google.com/drive/folders/1_Mge4EZtgGXtJzoNKZagGAjBSY5iwD94?usp=sharing
