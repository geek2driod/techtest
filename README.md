# Cryptocurrency List

This project is a simple cryptocurrency list application demonstrating various Kotlin and Jetpack Compose principles, designed according to specific UI and essential requirements.

---

## Project UI Requirements

The application meets the following UI requirements:

- **Display List of Coins**: Presents a list of cryptocurrency coins with relevant details.
- **Alphabetical Ordering**: The list is ordered alphabetically by the name of each coin.
- **Entry Dividers**: Each entry is separated by a divider for better readability.
- **Additional Coin Info**: Extra coin information is accessible through a popup or fragment.
- **Refresh Option**: A refresh button is provided to reload the list and ensure data freshness.
- **Error Handling**: Displays an error message if the list cannot be loaded (e.g., due to network issues).
- **List Item Animation**: Animates the list items for a smoother user experience.

## Project Essential Requirements

The application was built with the following essential requirements:

- **Kotlin**: Written entirely in Kotlin.
- **Jetpack Compose**: Used for building the UI with a modern, declarative approach.
- **Kotlin Coroutines**: Employed for asynchronous data handling.
- **Unit Tests**: Comprehensive unit tests have been written for repositories and viewmodels.
- **No Third-Party Libraries**: The project avoids using third-party libraries to maintain simplicity and transparency.
- **Git Delivery**: Final project is delivered via Git.

---

## Architecture

The application follows the **Model-View-ViewModel (MVVM)** architecture and adheres to **Clean Architecture** principles. Key layers include:

- **Data Layer**: Responsible for data sources and caching.
- **Domain Layer**: Contains business logic.
- **Presentation Layer**: Manages the UI and user interactions using ViewModel and UI layers.

---

## Libraries and Frameworks

The following libraries and frameworks are used:

- **Kotlin**: The primary language for building Android apps.
- **Jetpack Compose**: Used for building native Android UI.
- **Kotlin Coroutines**: For asynchronous data fetching and handling.
- **Kotlin Coroutines Flow**: To handle asynchronous data streams.
- **ViewModel**: Manages UI-related data that persists across configuration changes.
- **Dependency Injection (DI)**: Hilt is used to simplify dependency management within the app.
- **Retrofit**: A type-safe HTTP client for network calls and API interactions.
- **Room**: Used for local data caching within the app.
- **Mockito**: Facilitates mocking in unit tests for viewmodels and repositories.

---

## Getting Started

### Prerequisites
- Android Studio Ladybug (or newer)
- Minimum SDK 24
- Recommended: Familiarity with Jetpack Compose and Kotlin

### Installation
1. Clone the repository: 
   ```bash
   git clone https://github.com/geek2driod/techtest.git
   ```
2. Open the project in Android Studio.
3. Sync the project with Gradle files.

### Building and Running
To build and run the app:
1. Connect an Android device or start an emulator.
2. Select the `Run` configuration.
3. Press `Run` to build and deploy the application.

---

## Testing

Unit tests for the repositories and viewmodels are included. To run the tests:
1. In Android Studio, navigate to the `Test` section.
2. Right-click on the test file or directory and select `Run`.

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

