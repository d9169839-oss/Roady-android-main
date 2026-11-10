# Android permissions and connectivity behavior

## Manifest permissions

- `INTERNET`: allows the app to call its discovery backend.
- `ACCESS_NETWORK_STATE`: allows the app to observe current network connectivity.
- `ACCESS_COARSE_LOCATION` and `ACCESS_FINE_LOCATION`: foreground location choices for nearby discovery. Background location is intentionally not declared.
- `POST_NOTIFICATIONS`: declaration required for notification permission handling on Android 13 (API 33) and newer.

## Runtime permission behavior

Manifest declarations do not grant dangerous permissions. The app must request location at runtime when the user chooses **Find nearby help**. Request coarse and fine together so the user can choose approximate or precise location. The discovery experience must explain if approximate location is sufficient and handle denial without blocking the rest of the app.

Notification permission should be requested contextually when a user enables a feature that genuinely sends notifications. Do not request it on first launch merely because the manifest contains the permission. On Android versions before API 33, no runtime notification permission prompt is needed.

RoadHelp must not request background location for its current product scope.

## Offline status behavior

`ConnectivityMonitor` observes Android's default network using `registerDefaultNetworkCallback`. It considers the device online only when the active network has both `NET_CAPABILITY_INTERNET` and `NET_CAPABILITY_VALIDATED`. This avoids treating a connected Wi-Fi network with no internet access as online.

The monitor starts in `MainActivity.onStart` and unregisters in `onStop`. It uses callbacks rather than polling or a background service. Compose observes its `StateFlow`; when internet is unavailable, `HomeScreen` displays a banner outside the scrolling list so the status remains visible while the user scrolls. The banner disappears automatically after Android reports validated internet again.

This indicator describes general internet availability; it does not prove the RoadHelp backend itself is healthy. Backend/API errors must have their own UI state.
