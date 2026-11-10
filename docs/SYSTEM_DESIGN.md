# RoadHelp — Android client system design

## Product
RoadHelp is a global roadside-service discovery utility. It locates providers near the user's current foreground location and displays them nearest-first. Users contact providers through installed phone, WhatsApp, SMS, email or navigation apps. It is not a dispatch marketplace: no bookings, technician assignment, fleet operations, in-app service chat or service-payment flow.

## MVP journey
1. User opens RoadHelp.
2. Home presents "Find nearby help" and all provider categories selected.
3. User taps the action; app explains and requests foreground location permission.
4. App obtains a location fix and calls the RoadHelp API with 25 km radius and category=all.
5. App renders loading, success, empty, permission-denied, location-disabled, offline and retry states.
6. Results are sorted by distance ascending. Category filters are local.
7. User taps a contact action; Android opens the relevant external app. RoadHelp never auto-sends or auto-calls.
8. On foreground location changes, refresh only if movement is >=300 m from the last successful search origin AND results are older than five minutes. Debounce and cancel stale requests.

## Locked rules
- Radius: 25 km.
- Categories: all five by default (tow, tyre, mechanic, battery, fuel).
- Foreground location only; no background permission, service or periodic alarm.
- List first, map secondary.
- Nearest-first organic order.
- No mandatory login.
- Use ACTION_DIAL, not ACTION_CALL.
- Backend owns Google Places and other provider-source credentials.

## Architecture
presentation -> domain <- data.
- Presentation: Compose screens, ViewModels and immutable state.
- Domain: framework-free models, repository contracts and use cases.
- Data: Retrofit/API DTOs, mappers, repository implementation and source-aware cache.
- Core: DI, configuration, logging, dispatchers, location adapter and Android intent launcher.
Domain must not depend on Android, Retrofit, Room or Compose. Composables render state and emit events only. ViewModels coordinate use cases. Repositories own data access.

## Suggested packages
com.roadhelp.app/core/{config,design,di,location,platform}
com.roadhelp.app/domain/{model,repository,usecase}
com.roadhelp.app/data/{remote/api,remote/dto,mapper,repository}
com.roadhelp.app/presentation/{home,provider,map,settings}

## Location and refresh
- Request foreground location only; no background tracking.
- Do not track while app is backgrounded.
- A last-known fix may be used temporarily only with a freshness indicator.
- Save search origin only after successful provider response.
- Movement threshold: 300 metres.
- Freshness threshold: five minutes.
- Refresh requires BOTH thresholds. A timer alone never triggers a search.
- Debounce rapid fixes, cancel superseded requests and ignore responses for old search generations.
- Manual refresh is user-triggered and rate-limited.
- Do not call provider API for local sort/filter changes.

## Provider cards
May show name, category, distance, address, rating/count, open status, verified marker and contact actions only for available fields. Do not infer open status without trusted hours and provider-local timezone. Hide actions when contact data is absent. Preview fixtures must be labelled and excluded from production.

## Contact actions
- Phone: ACTION_DIAL with tel: URI; never ACTION_CALL and no CALL_PHONE permission.
- SMS: ACTION_SENDTO with smsto: URI.
- WhatsApp: wa.me URI using E.164 digits; graceful fallback if unavailable.
- Email: ACTION_SENDTO with mailto: URI.
- Directions: geo: or Google Maps directions URI.
- Any prefilled message is user-reviewed; never send automatically.
- Handle missing apps and malformed values without crashing.

## API and source policy
Android talks only to the RoadHelp backend. Do not bundle Google Places web-service keys. Backend returns a stable normalised provider contract. Respect source attribution, retention and caching terms; do not persist Google Places content unless terms explicitly allow it. Own-provider cache follows the app's own data policy.

## Quality
Explicit loading/content/empty/recoverable-error/permission states. Bounded retry for transient network errors. No automatic retry of contact actions. Accessible labels, touch targets, contrast and dynamic text. Metric/imperial formatting follows locale or user preference. Use E.164 phone values and provider-local timezone from API. Minimise location analytics.

## Tests
Test category mapping, local filtering, distance formatting, movement/freshness policy, DTO mapping, repository error mapping and ViewModel transitions. UI tests cover permission explanation, loading/empty/error states, category filtering and contact affordances.
