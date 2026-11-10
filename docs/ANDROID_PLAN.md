# RoadHelp Android implementation plan

## Goal
A global Android client for nearby roadside-service discovery and direct contact. It is not a dispatch, booking, payment or fleet-management product.

## Locked decisions
- 25 km initial radius.
- All five categories selected by default: towing, tyre, mechanic/repair, battery and fuel/roadside.
- Organic results sorted nearest-first.
- Foreground location only; no background permission/service/alarm.
- Refresh only when movement is at least 300 m AND the last successful results are older than five minutes. Five minutes is a freshness gate, not polling.
- Debounce location changes, cancel superseded requests and ignore stale responses.
- List-first home; map is secondary.
- No mandatory authentication.
- Contact actions use external apps and require explicit user taps.

## Architecture
Use Clean Architecture: presentation -> domain <- data.
- Presentation: Compose, ViewModels, immutable UiState, one-way events.
- Domain: framework-free entities, repository interfaces and use cases.
- Data: API DTOs, mappers, repository implementations and source-aware cache.
- Core: DI, configuration, location/platform abstractions and design system.

Domain must not depend on Android, Retrofit, Room or Compose. Composables render state and emit events. ViewModels orchestrate use cases. Repositories own data access. Dependencies are constructor-injected.

## Package layout
com.roadhelp.app/
- core/design, core/config, core/di, core/location, core/platform
- domain/model, domain/repository, domain/usecase
- data/remote/api, data/remote/dto, data/mapper, data/repository
- presentation/home, presentation/provider, presentation/map, presentation/settings

## Sequence
1. Stabilise Gradle/SDK versions, add wrapper and CI build verification.
2. Implement foreground location permission and location provider.
3. Implement/test pure movement + freshness refresh policy.
4. Implement API client, DTO mapping and repository.
5. Implement discovery use case and ViewModel state machine.
6. Add external contact/navigation intents.
7. Add map/list toggle, accessibility, localisation and release readiness.

## Non-functional requirements
- Never fabricate location or provider data.
- Sample fixtures must be visibly labelled and excluded from production.
- Do not persist external place content unless the source terms permit it.
- Keep third-party discovery keys on the server.
- Use E.164 phone values and provider-local timezone data supplied by the API.
- Handle permission denial, disabled location, offline, timeout, empty results and partial contact data.
- Requests must be cancellable and stale responses must not replace newer results.

## Acceptance criteria
- Search begins only after user-visible location permission.
- Results are within radius and sorted nearest-first.
- Local category filtering does not cause provider API requests.
- Movement refresh respects both gates.
- Contact actions launch only after explicit user interaction and fail gracefully.
- Tests cover refresh policy, filtering, formatting, mapping and ViewModel transitions.
- CI and release build pass; device testing is recorded honestly.
