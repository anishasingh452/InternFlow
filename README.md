# InternFlow

InternFlow is a lightweight Android app simulating iOS-style development team workflows for tracking tasks and identifying blockers in intern teams. It gives mentors a quick dashboard for task progress, ownership visibility, and stale in-progress work that may need attention.

## Tech Stack

- Kotlin
- MVVM
- Repository pattern
- Room database
- StateFlow
- Jetpack Navigation Component
- XML layouts with Material 3 components
- RecyclerView task list

## Core Flow

1. Dashboard shows total, in-progress, completed, and attention-needed task counts.
2. Add Task captures title, status, difficulty, and timestamp.
3. Task List shows all saved tasks with status color, difficulty, relative time, and attention badges.

## Blocker Logic

A task is marked as needing attention when:

```text
status == "In Progress" && current time - timestamp > 24 hours
```

This keeps the product simple while still modeling a real development-team signal: work that has been active too long deserves mentor visibility.

## Run

Open this folder in Android Studio, let Gradle sync, then run the `app` configuration on an emulator or Android device.
