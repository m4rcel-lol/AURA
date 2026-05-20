# AURA 🌌

AURA is an ultra-sleek, interactive Android home hub launcher screen engineered in **Kotlin** using state-of-the-art **Jetpack Compose (Material 3)**. Styled with carbon-dark canvases, electric blue system highlights, and electric violet accents, AURA offers a highly polished UI reflecting modern computing design paradigms.

This repository hosts the native Android modules that implement Clean Architecture and Material Design guidelines.

---

## 🎨 Visual Identity & Material Theme

AURA breaks away from generic presets to paint a high-contrast console-infused layout:

*   **Deep Charcoal Dark Mode**: Employs an eye-safe `#08080A` background aligned with pristine negative space.
*   **Electric Highlights**: High-contrast modern electric blue (`#1E80FF`) leads interaction cues, alongside subtle electric violet accent details.
*   **Tactile Borders**: Component panels are bordered by crisp, low-opacity strokes, utilizing rounded 16dp to 24dp shapes for structural balance.

All custom colors and themes are modularized within `Color.kt` and `Theme.kt`.

---

## 🏗️ Architecture Blueprint

The codebase implements a standard **Clean Architecture** framework divided into distinct layers:

```
dev.m5rcel.aura/
├── data/           # Repository implementations, local/network data sources, entities
├── domain/         # Pure Business logic layer: Uses Cases, models, repository abstractions
│   ├── model/      # Core Data representations (e.g., WeatherData, Note, Reminder, CalendarEvent)
│   └── usecase/    # Core logic interactors
├── presentation/   # Presentation layer built entirely on declarative Jetpack Compose UI
│   ├── components/ # Granular reusable Material 3 cards/widgets
│   └── dashboard/  # Dashboard screen, state management, and ViewModels
├── di/             # Compile-time Dependency Injection bindings
└── notification/   # Local background and foreground push alerts managers
```

---

## 📱 Detailed Component Grid (10 Core Widgets)

The cockpit contains 10 interactive widgets meticulously styled to present system resources beautifully:

1.  **GreetingCard** (`GreetingCard.kt`): A time-aware greeting panel displaying custom greetings depending on local timezone hour partitions (Morning, Afternoon, Evening) paired with elegant display headers.
2.  **WeatherWarningBannerCard** (`WeatherWarningBannerCard.kt`): Alerts the user to extreme environments by rendering high-severity indicators in elegant reds or warning gold.
3.  **WeatherCard** (`WeatherCard.kt`): A sleek meteorological feed tracking temperatures, wind speeds, and relative humidity indices with custom icon cues.
4.  **RecentAppsCard** (`RecentAppsCard.kt`): Offers horizontal launching of system apps showing specific usage metrics with fine detail chips.
5.  **NotesCard** (`NotesCard.kt`): Serves persistent notes, displaying tags in color-coded indicator dots with pin support and delete animations.
6.  **RemindersCard** (`RemindersCard.kt`): Manages scheduled checkpoints with interactive completion checkboxes and strike-out styling.
7.  **CalendarCard** (`CalendarCard.kt`): Keeps calendar sync loops organized, showcasing upcoming chronologically grouped team synces and audits.
8.  **QuickTogglesCard** (`QuickTogglesCard.kt`): Immediate control filter chips for toggling hardware states such as Wi-Fi, Bluetooth, and Quiet/Silent modes with active container highlights.
9.  **BatteryCard** (`BatteryCard.kt`): Visualizes immediate battery cells, charging currents, and power attachments.
10. **StorageCard** (`StorageCard.kt`): Highlights system disk partitions using linear progress bars and specific gigabyte fractions.
11. **MusicPlayerCard** (`MusicPlayerCard.kt`): A lofi tracks player integrating responsive media buttons, progress ticks, and album details.

---

## 🛠️ Native Technical Stack

*   **Language**: 100% Kotlin with structured Coroutines & Flow APIs.
*   **UI Framework**: Declarative Jetpack Compose using Material 3 UI libraries.
*   **State Engines**: Unidirectional Data Flow (UDF) managed by Android `ViewModel` pushing `StateFlow` structures directly to Compositions.
*   **Theme Integration**: Customized Dynamic Material System conforming to `darkColorScheme` and `lightColorScheme` templates.

---

## 💻 Building and Executing

To compile and launch the application locally on an Android device or emulator:

### Prerequisites
*   Android Studio Ladybug (or newer recommended)
*   JDK 17+ configured in your local path

### Gradle Compilation
Run terminal directives directly inside the `/android` directory:

```bash
# Force-compile clean debug apks
./gradlew clean assembleDebug

# Run unit verification tests
./gradlew test
```

The compiled binaries will be output to:
`/android/app/build/outputs/apk/debug/app-debug.apk`
