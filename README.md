# RoadHelp Android

**RoadHelp helps drivers find nearby roadside-service providers and contact them directly.**

RoadHelp is a global roadside-service discovery app for Android. It helps drivers find nearby towing, tyre repair, battery assistance, mechanics and other roadside providers, ranked by distance, with one-tap calling, WhatsApp, SMS, email and navigation through apps already installed on the user's device. No booking marketplace, dispatch service or mandatory account.

> **MVP principle:** RoadHelp is a discovery and direct-contact utility, not a roadside dispatch marketplace. It does not book jobs, assign technicians, process service payments, or operate a fleet.

## Product at a glance

- Global roadside provider discovery, starting with the user's foreground location.
- Initial search radius: **25 km**.
- All supported provider categories shown by default: towing, tyre services, battery assistance, mechanics/repair, and fuel/emergency roadside help.
- Providers sorted nearest first, with distance and available contact details.
- Direct actions open the user's installed apps: phone dialler, WhatsApp, SMS, email, and maps.
- No mandatory sign-in.
- No background location tracking.
- Optional voluntary support/tips may be considered after validating Google Play billing requirements.
- Sponsored provider placements are a later phase and must be clearly labelled without silently displacing organic nearest-first results.

## Android architecture

Kotlin + Jetpack Compose + Material 3, following:

`presentation -> domain <- data`

- **Presentation:** Compose screens, ViewModels, immutable UI state and UI event handling.
- **Domain:** provider models, repository contracts, category definitions and use cases. No Android framework dependencies.
- **Data:** API DTOs, repository implementations, mapping, and policy-compliant local caching.
- **Core:** design system, configuration, location and platform integration abstractions.

## Locked MVP decisions

| Decision | Value |
|---|---|
| Initial radius | 25 km |
| Default categories | All |
| Refresh policy | Refresh only when the user has moved at least 300 m **and** the current results are older than 5 minutes |
| Location access | Foreground only |
| Home layout | Provider list first; map is secondary |
| Ranking | Nearest first |
| Authentication | Not required |
| Calling | `ACTION_DIAL`, never `ACTION_CALL` |
| Background tracking | Not permitted in MVP |

The 5-minute value is a freshness condition, not a timer that independently triggers network requests. Location changes must be debounced and search requests must be cancellable. Manual refresh remains available with sensible throttling.

## Development status

This repository is the Android client. The backend is a separate service/repository and owns provider aggregation and third-party discovery credentials. Provider preview data must always be labelled as sample data until connected to the real API.

See [docs/SYSTEM_DESIGN.md](docs/SYSTEM_DESIGN.md), [docs/ANDROID_PLAN.md](docs/ANDROID_PLAN.md), and [docs/PROGRESS.md](docs/PROGRESS.md) for implementation requirements and progress.

## Safety, privacy and provider-data rules

- Never request background location for this MVP.
- Request location permission only when the user invokes nearby discovery or an equivalent clear feature.
- If permission is denied, explain the limitation and allow retry; do not silently fall back to a fabricated location.
- Google Places web-service credentials must remain on the server.
- Respect each provider source's attribution, retention, caching and display terms. Do not persist third-party place content unless the applicable terms explicitly permit it.
- Never commit API keys, signing credentials, keystores, or machine-local configuration.
- Contact actions must be user-initiated. RoadHelp must not automatically call, message, or email a provider.

## Repository scope

This repository contains only the Android app. Backend code, provider aggregation, database migrations, deployment configuration and provider-source API keys belong in the separate RoadHelp server repository.

## Repository description

RoadHelp Android — a global roadside-service finder that shows nearby towing, tyre, battery and repair providers, sorted by distance, with direct call, WhatsApp, SMS, email and navigation actions.

