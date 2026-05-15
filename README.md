# Namma-Skill (Training Opportunity Tracker)

An Android (Kotlin + XML) demo app that helps rural youth discover **upcoming vocational course batches**, filter them (trade + **Short/Long term**), view centers on a map, and apply with **one tap**.

## Open in Android Studio (no version errors)
1. Open **Android Studio** → **Open** → select this `NammaSkill` folder.
2. Let Gradle sync finish.
3. Run on an emulator/device.

## Notes
- The project runs **offline-first** with sample data (no Firebase keys needed).
- Notifications are demonstrated using **WorkManager** (periodic local alerts based on your favorite trades set in Profile).
- Maps: to see real map tiles, put your Google Maps key in `app/src/main/res/values/strings.xml` (`google_maps_key`).

## Firebase (optional next step)
This template is ready to be upgraded to Firebase (Firestore + FCM). If you want, tell me:
- which district/state you want,
- your Firebase project name,
- and whether you prefer Firestore or Realtime Database,
and I can add Firebase modules + schema + sync logic.

