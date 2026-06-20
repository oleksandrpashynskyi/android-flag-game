# Android Flag Game

A native Android quiz game (Java) that tests your knowledge of world flags. The app shows a country name and a grid of flags; tap the flag that matches to advance. Difficulty ramps across four levels as more flags appear on screen.

## Gameplay

- The game runs through **four levels**, showing **3 → 6 → 9 → 12** flags respectively.
- Each round displays a target country name; the player taps the matching flag.
- **Scoring:** a correct answer awards points that scale with the level (+1, +2, +4, +8) and advances to the next level; a wrong answer deducts a point (never below zero) and stays on the level.
- After level 4, an end screen (`EndGameActivity`) shows the final score and the number of correct/wrong answers.

Flag images are bundled as app assets, organized by continent (`Africa`, `Asia`, `Europe`, `North_America`, `Oceania`, `South_America`), and loaded at runtime via the `AssetManager`. Country names are derived from the image file names.

## Tech stack

- **Language:** Java
- **Platform:** Android (AndroidX / AppCompat)
- **Build:** Gradle (Kotlin DSL, version catalog) — `compileSdk 34`, `minSdk 24`, `targetSdk 34`
- **Package:** `com.example.project4`

## Project structure

```
android-flag-game/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/project4/
│       │   ├── MainActivity.java       # game loop: pick flags, check answers, score
│       │   └── EndGameActivity.java    # end-of-game results screen
│       ├── res/                        # layouts, drawables, values
│       └── assets/<Continent>/*.png    # flag images grouped by continent
├── build.gradle.kts
├── settings.gradle.kts
└── gradlew / gradlew.bat
```

## Build and run

1. Open the project in **Android Studio**.
2. Let Gradle sync, then run on an emulator or device (API 24+).

Or build a debug APK from the command line:

```bash
./gradlew assembleDebug
# Output: app/build/outputs/apk/debug/app-debug.apk
```
