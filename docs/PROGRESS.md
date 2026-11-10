# Android implementation progress

## Foundation
- [x] Kotlin/Compose project and Material 3 theme scaffold.
- [x] Separate Android Activity from the application-level Compose root.
- [x] Split Home into a screen file and focused reusable composables.
- [x] Move preview-only provider fixtures out of UI components.
- [x] Keep connectivity observation in the platform/core layer and show offline state in Home.
- [x] Remove redundant Kotlin Android plugin application for AGP 9+.
- [x] Raise compileSdk to 37 to satisfy the selected Compose BOM's Android API requirement.
- [ ] Confirm the updated build and unit tests in CI.
- [ ] Add navigation when the first multi-screen workflow is introduced.
- [ ] Introduce DI when dependency wiring grows beyond the current small app graph.

## Location milestone
- [x] Request approximate or precise location only after the user taps **Find nearby help**.
- [x] Accept approximate location as sufficient for nearby discovery.
- [x] Add a one-shot, balanced-power Fused Location Provider adapter.
- [x] Keep Android/Google Play Services location types out of domain contracts.
- [x] Expose location loading, success and failure messages through HomeUiState.
- [x] Keep the user's coordinates in transient ViewModel state only; no persistence or continuous tracking.
- [x] Add pure 300 m movement + five-minute freshness refresh policy.
- [x] Add unit tests for coordinate validation and refresh-policy thresholds.
- [ ] Verify permission dialogs and actual location retrieval on emulator/device.
- [ ] Handle permanently denied permission with a contextual app-settings route if device testing shows it is needed.

## Feature milestones
1. **Discovery:** API client, DTO mapping, repository, use cases, 25 km radius, nearest-first ordering, category filtering, loading/empty/error states.
2. **Provider list and map:** independent Compose screens and ViewModels; list remains the primary experience, map is secondary.
3. **Provider actions:** user-initiated call, SMS, WhatsApp, email and maps intents.
4. **Reliability:** app-wide offline status, backend-health distinction, retry and resilient state handling.
5. **Release readiness:** tests, accessibility, performance, localisation, policy review and release build.

## Product constraints
- Initial search radius: 25 km.
- Refresh only when the user has moved at least 300 m **and** the last successful results are at least five minutes old.
- Foreground location only. No background location, tracking, or polling.
- No mandatory account or dispatch/booking marketplace.
- Organic results sorted nearest-first.
- Sample records must remain visibly labelled as samples; never present them as live providers.
- External contact/navigation actions happen only after an explicit user tap.
- Do not prompt for notifications until a user-facing notification feature needs them.

## Architecture conventions
- `MainActivity` owns Android lifecycle integration and creates the platform adapter/ViewModel graph.
- `RoadHelpApp` is the Compose application root; global theme/navigation/status concerns belong there.
- Each user-facing feature owns its screen, UI state, ViewModel and feature-specific components.
- Reusable platform integrations belong in `core` or a data adapter; framework-independent rules and models belong in `domain`.
- Composables render state and emit events; they do not call repositories or network clients directly.
- Add abstractions when a real dependency or use case requires them; avoid speculative empty layers.

## Honest validation status
The last architecture-branch CI run failed at AAR metadata validation because the selected Compose libraries require compileSdk 37 while the project used 36. This branch updates compileSdk to 37. CI for this branch is pending; no successful build or emulator/device test is claimed yet.
