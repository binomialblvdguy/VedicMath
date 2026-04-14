# VedicMath - Quick Start Guide

## Opening in Android Studio

### Method 1: Direct Import (Recommended)
```bash
~/android-studio/bin/studio.sh ~/Projects/android/VedicMath
```

### Method 2: Via Android Studio Menu
1. Launch Android Studio: `~/android-studio/bin/studio.sh`
2. File → Open
3. Navigate to: `/home/j/Projects/android/VedicMath`
4. Click "OK"

## First-Time Setup

When you open the project in Android Studio:

1. **Gradle Sync**: Android Studio will automatically sync Gradle
   - This downloads all dependencies
   - Takes 2-5 minutes first time
   - Be patient! It's downloading the Android SDK components

2. **JDK Configuration**: 
   - Android Studio should auto-detect OpenJDK 17
   - If asked, select: File → Project Structure → SDK Location
   - Set: `ANDROID_HOME` = `/home/j/Android/Sdk`

3. **Build the Project**:
   - Build → Make Project
   - Or press: Ctrl+F9

4. **Run the App**:
   - Run → Run 'app'
   - Select an emulator or connected device
   - App launches!

## Project Structure

```
VedicMath/
├── app/
│   ├── src/main/
│   │   ├── kotlin/com/vedicmath/app/     # All Kotlin code
│   │   │   ├── ui/screens/              # UI Components
│   │   │   ├── domain/models/           # Data structures
│   │   │   ├── data/repository/         # Data access
│   │   │   └── math/                    # Calculation engine
│   │   └── res/                         # Resources
│   │       ├── layout/                  # XML layouts
│   │       └── values/                  # Strings, colors
│   └── build.gradle.kts                 # Build config
├── build.gradle.kts                     # Root build config
├── settings.gradle.kts                  # Gradle settings
└── README.md                            # Full documentation
```

## Building from Command Line

```bash
# Navigate to project
cd ~/Projects/android/VedicMath

# Ensure environment is set
source ~/.bashrc

# Build the APK
./gradlew assembleDebug

# Output APK location:
# app/build/outputs/apk/debug/app-debug.apk
```

## Key Files to Explore

### Start Here:
1. `app/src/main/kotlin/com/vedicmath/app/ui/screens/MainActivity.kt`
   - Main navigation controller
   - Understands flow between screens

2. `app/src/main/kotlin/com/vedicmath/app/domain/models/MultiplicationMethod.kt`
   - Data structures
   - Understands what data the app manipulates

3. `app/src/main/kotlin/com/vedicmath/app/math/VedicCalculatorFragment.kt`
   - Calculation logic
   - Understands HOW methods are implemented

### Then Explore:
4. `app/src/main/kotlin/com/vedicmath/app/data/repository/PedagogyRepository.kt`
   - Educational content
   - Observations and insights (the "teach" part)

5. `app/src/main/kotlin/com/vedicmath/app/ui/screens/fragments/`
   - All screen implementations
   - UI logic and user interaction

## Testing the App

### Create Virtual Device (if needed)
```bash
# In Android Studio:
Tools → Device Manager → Create Device
# Choose a device (e.g., Pixel 6)
# Choose API level 35
# Create and start the emulator
```

### Run the App
1. Run → Run 'app'
2. Select your emulator
3. App launches!

## Troubleshooting

### "Gradle sync failed"
- Check internet connection
- Wait for background tasks to complete
- Try: Build → Rebuild Project

### "SDK not found"
- Check: File → Project Structure → SDK Location
- Should be: `/home/j/Android/Sdk`
- If missing, run: `source ~/.bashrc` then reopen Android Studio

### "Cannot find symbol" errors
- Run: Build → Clean Project
- Then: Build → Rebuild Project
- File → Invalidate Caches (if still failing)

### Emulator won't start
- Device Manager → Select device → Play button
- May take 2-3 minutes first time
- Check: Tools → Device Manager for details

## Understanding the App Flow

### User Journey:
```
1. Launch App → MainActivity shows MainScreenFragment
2. Click "Simple Multiplier" → MethodSelectionFragment (choose 3-12)
3. Click "5" → SolutionFragment (ready to calculate)
4. Enter numbers (e.g., 234 and 5) → Click Calculate
5. See solution steps and Vedic format
6. Click "Observations" → ObservationsFragment (learn WHY it works)
7. Click "Back" → Returns to previous screen
8. Continue navigating or exit
```

### Key Screens:

**MainScreenFragment** - Method selection
- 6 buttons for 6 different methods
- Clean, organized interface

**MethodSelectionFragment** - Multiplier selection (for Simple Multiplier only)
- Shows ratio table
- Allows choosing which multiplier to use

**SolutionFragment** - Calculator interface
- Input boxes for numbers
- Calculate button
- Shows step-by-step solution
- Clear button for practice
- Observations button to learn more

**ObservationsFragment** - Educational insights
- Explains how method works
- Shows weaknesses/limitations
- Provides examples
- Lists related methods

## Customizing the App

### Adding a New Observation:
1. Edit: `data/repository/PedagogyRepository.kt`
2. Find the appropriate `getXXXObservations()` method
3. Add new `MethodObservation` object to the list
4. App automatically displays it

### Changing Colors:
1. Edit: `res/values/colors.xml`
2. Modify color values (hex format)
3. Changes apply app-wide

### Changing Text:
1. Edit: `res/values/strings.xml`
2. Modify string values
3. Changes apply app-wide

## Development Tips

### Hot Reload (Compose only, not yet in this app)
- Makes changes appear instantly
- Not available with traditional fragments yet

### Logcat (Debug Output)
- View → Tool Windows → Logcat
- See all app messages
- Useful for debugging

### Android Profiler
- View → Tool Windows → Profiler
- Monitor CPU, memory, network usage
- Identify performance issues

### Debugger
- Set breakpoints by clicking line numbers
- Run → Debug 'app'
- Step through code

## Next Steps

### Explore the Code:
1. Read MainActivity to understand navigation
2. Read MainScreenFragment to see button setup
3. Read SolutionFragment to see how calculation works
4. Read VedicCalculator to understand math logic
5. Read PedagogyRepository to see pedagogical content

### Make Changes:
1. Modify a color in colors.xml
2. Rebuild and see changes
3. Modify a string in strings.xml
4. Rebuild and see changes
5. Try modifying a layout XML
6. Get comfortable with the development cycle

### Learn More:
1. [Android Developer Guide](https://developer.android.com/guide)
2. [Kotlin Documentation](https://kotlinlang.org/docs)
3. [Material Design](https://material.io)
4. [Vedic Mathematics](https://www.vedicmathsindia.org/)

## Success Indicators

You'll know the app is working when:
- [ ] App launches in emulator
- [ ] Main screen shows 6 method buttons
- [ ] Can click buttons and navigate
- [ ] Can enter numbers and calculate
- [ ] See step-by-step solutions
- [ ] Can read observations
- [ ] Can navigate back and forth
- [ ] App doesn't crash!

## Build System Details

### Gradle Version: 8.4
### Kotlin Version: 1.9.20
### compileSdk: 35
### minSdk: 26
### targetSdk: 35

These are configured in:
- `build.gradle.kts` (root)
- `app/build.gradle.kts` (app-specific)

## Memory & Performance

### Expected Performance:
- Launch time: 2-3 seconds
- Calculation: Instant (<100ms)
- Navigation: Smooth
- Memory usage: ~100-150 MB

### Optimization (if needed):
- Gradle properties already configured
- `~/.gradle/gradle.properties` has optimizations
- 4GB memory allocated for builds

## Getting Help

### Within Android Studio:
1. Help → Find Action → Search for help topics
2. Help → Android Studio Help
3. Built-in documentation

### From Command Line:
```bash
cd ~/Projects/android/VedicMath
./gradlew help       # Show gradle help
./gradlew tasks      # Show available gradle tasks
```

## Success! 🎉

Once you can:
1. Build the app without errors
2. Run it in an emulator
3. Navigate through all screens
4. Calculate using different methods
5. Read pedagogical observations

You have a fully functional Vedic Math education app!

---

**Questions?** Check the README.md for more detailed information.
**Ready to explore?** Open Android Studio and dive in! 🚀
