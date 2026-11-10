# Decisions and open questions

## Locked
- 25 km radius.
- All categories selected by default.
- Refresh when >=300 m movement AND results are older than 5 minutes.
- Foreground-only location; no background service or AlarmManager polling.
- List-first UI; map secondary.
- Direct external contact; no dispatch or booking.
- No mandatory account.

## Resolve before release
- Production API base URL and environment configuration.
- Play target SDK requirements at release time.
- Provider-source attribution, retention and caching compliance.
- Supported locales and metric/imperial defaults.
- Final app icon, screenshots, privacy policy URL and store listing assets.
- Map SDK selection and key restrictions if map view is enabled.

## Do not block UI work
Use clearly labelled local sample data until backend is available. Never invent provider phone numbers or present sample businesses as live listings.
