# Android implementation progress

## Bootstrap
- [x] Product description and locked MVP decisions.
- [x] Kotlin/Compose Gradle project structure.
- [x] Initial Material 3 theme and list-first home screen.
- [x] Provider category enum and framework-free provider model.
- [x] Category unit tests and GitHub Actions workflow.
- [ ] Add Gradle wrapper and verify toolchain compatibility.
- [ ] Run a real emulator/device build; no build is claimed yet.

## Platform foundation
- [x] Declare internet and network-state permissions.
- [x] Declare foreground approximate and precise location permissions (no background location).
- [x] Declare Android notification permission.
- [x] Add event-driven validated-internet monitoring tied to Activity foreground lifecycle.
- [x] Show a persistent offline banner above the home content and hide it when validated internet returns.
- [ ] Add runtime location permission flow when nearby discovery is wired.
- [ ] Add contextual notification permission request when a notification feature is introduced.

## Next phases
1. Foundation: verify Gradle/SDK, add Hilt, navigation and architecture tests.
2. Location: request foreground permission, obtain device location, enforce 300 m movement threshold and five-minute freshness gate.
3. Discovery: API client, DTO mapping, repository, use cases and UI loading/error/empty states.
4. Contact: safe call, WhatsApp, SMS, email and maps intents.
5. Quality: accessibility, localisation, policy review, tests and release hardening.

## Honest status
The current home screen still uses clearly labelled sample listings. The manifest declares permissions, but runtime location and notification permission prompts are not implemented yet. Connectivity monitoring is implemented for the foreground Activity and drives a persistent offline banner. A successful Android build or device test has not yet been verified.
