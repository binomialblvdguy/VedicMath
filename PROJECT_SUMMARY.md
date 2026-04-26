# VedicMath - Project Summary

## Current Status

- Current known good commit: `80f0f6f`
- Status: ✅ builds and tests passing
- Verified commands:
    - `./gradlew :app:assembleDebug`
    - `./gradlew :app:testDebugUnitTest`

---

## Project Overview

VedicMath is an Android educational app for exploring Vedic mathematics techniques through:

- guided multiplication workflows
- square and cube workflows
- auto-selected solving paths
- cross-product quiz practice
- explanatory, step-based output

The project currently supports both calculator-style use and quiz-style practice.

---

## Current Architecture

Project root:

```text
VedicMath/
├── app/src/main/kotlin/com/vedicmath/app/
│   ├── adapters/
│   ├── data/
│   ├── domain/
│   │   └── models/
│   ├── models/
│   └── ui/
│       └── screens/
│           └── fragments/
├── README.md
├── QUICKSTART.md
└── PROJECT_SUMMARY.md
