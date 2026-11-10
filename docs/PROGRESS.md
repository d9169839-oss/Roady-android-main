# Android implementation progress

## Bootstrap
- [x] Product description and locked MVP decisions.
- [x] Kotlin/Compose Gradle project structure.
- [x] Initial Material 3 theme and list-first home screen.
- [x] Provider category enum and framework-free provider model.
- [x] Category unit tests and GitHub Actions workflow.
- [ ] Add Gradle wrapper and verify toolchain compatibility.
- [ ] Run a real emulator/device build; no build is claimed yet.

## Next phases
1. Foundation: verify Gradle/SDK, add Hilt, navigation and architecture tests.
2. Location: foreground permission, location provider, 300 m movement threshold, five-minute freshness gate, debounce.
3. Discovery: API client, DTO mapping, repository, use cases and UI loading/error/empty states.
4. Contact: safe call, WhatsApp, SMS, email and maps intents.
5. Quality: accessibility, localisation, policy review, tests and release hardening.

## Honest status
The current home screen uses clearly labelled sample listings. It does not yet request location, call the backend or launch contact intents. The build has not been run in this environment.
