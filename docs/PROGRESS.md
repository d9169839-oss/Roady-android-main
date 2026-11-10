# Android implementation progress

## Foundation
- [x] Kotlin/Compose project and Material 3 theme scaffold.
- [x] Separate Android Activity from the application-level Compose root.
- [x] Split Home into a screen file and focused reusable composables.
- [x] Move preview-only provider fixtures out of UI components.
- [x] Keep connectivity observation in the platform/core layer and show offline state in the Home feature.
- [x] Correct the redundant Kotlin Android Gradle plugin application for AGP 9+.
- [ ] Confirm the corrected Gradle build and unit tests in CI.
- [ ] Add navigation and dependency injection when the first multi-screen workflow is introduced.

## Feature milestones
1. **Location:** foreground permission UX, device location provider, denied/disabled states, 300 m movement and five-minute freshness policy.
2. **Discovery:** API client, DTO mapping, repository, use cases, 25 km radius, nearest-first ordering, category filtering, loading/empty/error states.
3. **Provider list and map:** independent Compose screens and ViewModels; list remains the primary experience, map is secondary.
4. **Provider actions:** user-initiated call, SMS, WhatsApp, email and maps intents.
5. **Reliability:** app-wide offline status, backend-health distinction, retry and resilient state handling.
6. **Release readiness:** tests, accessibility, performance, localisation, policy review and release build.

## Product constraints
- Initial search radius: 25 km.
- Refresh only when the user has moved at least 300 m **and** the last successful results are at least five minutes old.
- Foreground location only. No background location, tracking, or polling.
- No mandatory account or dispatch/booking marketplace.
- Organic results sorted nearest-first.
- Sample records must remain visibly labelled as samples; never present them as live providers.
- External contact/navigation actions happen only after an explicit user tap.

## Architecture conventions
- `MainActivity` owns Android lifecycle integration only.
- `RoadHelpApp` is the Compose application root; global theme/navigation/status concerns belong there.
- Each user-facing feature owns its screen, UI state, ViewModel and feature-specific components.
- Reusable platform integrations belong in `core`; framework-independent rules and models belong in `domain`; external data mapping belongs in `data`.
- Composables render state and emit events; they do not call Android services, repositories or network clients directly.
- Add abstractions when a real dependency or use case requires them; avoid speculative empty layers.

## Honest validation status
The earlier Android CI run failed during Gradle plugin application because the project applied `org.jetbrains.kotlin.android` alongside AGP 9.5.1 built-in Kotlin support. This branch removes that redundant plugin. The corrected build has not yet completed; check the branch's CI run before claiming success. No emulator/device test has been performed.