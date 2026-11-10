# Android architecture and feature boundaries

This document defines the intended structure for RoadHelp Android development. It is deliberately incremental: directories and abstractions are added when a feature needs them rather than creating empty scaffolding.

## Package layout

```
com.roadhelp.app/
├── MainActivity.kt                 # Android lifecycle entry point
├── RoadHelpApp.kt                  # Compose application root
├── core/
│   ├── design/                     # Theme, tokens and shared design primitives
│   ├── network/                    # Connectivity observation
│   ├── location/                   # Connectivity and platform location integrations
│   ├── permissions/                # Runtime permission coordination
│   └── navigation/                 # Navigation graph when multiple screens are wired
├── domain/
│   ├── model/                      # Framework-independent business models
│   ├── repository/                 # Repository contracts
│   └── usecase/                    # Business rules and application actions
├── data/
│   ├── location/                   # Fused location adapter
│   ├── remote/                     # HTTP API and DTOs
│   └── repository/                 # Repository implementations/mappers
├── di/                             # Dependency injection modules when introduced
└── feature/
    ├── home/
    │   ├── HomeScreen.kt
    │   ├── HomeUiState.kt           # Add with the Home ViewModel milestone
    │   ├── HomeViewModel.kt         # Add when Home consumes repository-backed state
    │   ├── SampleProviderFixtures.kt
    │   └── components/
    ├── providers/
    │   ├── ProvidersScreen.kt
    │   ├── ProvidersViewModel.kt
    │   └── components/
    ├── map/
    │   ├── MapScreen.kt
    │   └── MapViewModel.kt
    └── providerdetail/
        ├── ProviderDetailScreen.kt
        └── ProviderDetailViewModel.kt
```

## Responsibility rules

| Area | Owns | Must not own |
|---|---|---|
| Activity | Android lifecycle and platform entry | Feature UI, business rules, API calls |
| Compose application root | Theme, navigation host, app-wide banners | Provider filtering or location calculations |
| Feature screen | Compose layout and rendering state | Android service access or HTTP implementation |
| Feature ViewModel | Screen state and event orchestration | Android `Context`, DTO parsing, UI widgets |
| Domain | Models, repository contracts, use cases and pure rules | Android framework or Compose |
| Data | API/local data sources, DTO mapping and repository implementations | Compose UI |
| Core | Cross-feature platform services and shared primitives | Feature-specific business decisions |

## Compose file convention

- One `*Screen.kt` per navigable screen.
- Reusable pieces specific to a feature go in that feature's `components/` folder.
- Keep a component in the feature package until a second feature genuinely reuses it.
- Keep screen state in a `*UiState.kt` data class when state becomes more than a few local presentation values.
- UI callbacks represent user intent; the ViewModel performs the action and publishes a new immutable state.
- Preview/sample content must be isolated and clearly labelled. It must never be mistaken for a live directory result.

## Delivery approach

Work in small feature branches and pull requests. A milestone is not complete until its unit tests and Android CI pass. Device-only behaviours (permission dialogs, location settings, external intents and map rendering) also require a real emulator/device check before release.