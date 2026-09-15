# SmartCam AI

SmartCam AI is a consent-based CCTV monitoring foundation for legitimate home,
safety, and transparent workplace monitoring. The Phase 1 app uses clearly
labelled demo data; it does not secretly monitor people, perform facial
recognition, or claim to know what a person is doing.

## Phase 1 Architecture

```text
Compose UI -> ViewModel/StateFlow -> Repository interface -> Room/DataStore
												 |
												 +-> AppResult/AppError and AppLogger
```

- `ui/`: Compose screens, Material 3 theme, and navigation destinations.
- `ui/dashboard/DashboardViewModel`: presentation state and lifecycle-aware
	collection.
- `data/repository`: repository contracts and the explicit
	`DemoSmartCamRepository`. Replace this implementation with a Room-backed
	repository when camera/event workflows are connected.
- `data/local`: Room entities, DAOs, enum converters, indexes, and foreign
	keys.
- `data/settings`: DataStore-backed theme, monitoring-notice, and retention
	preferences.
- `di/`: Hilt bindings for Room, repositories, logging, and detection.
- `core/`: shared error/result and logging foundations.
- `engine/`: detection interface and mock engine. Advanced AI is intentionally
	deferred to a later phase.

## Demo Data Boundary

The dashboard, camera list, events, and activity screens show demo values so
the UI can be explored immediately. Demo values are marked in the dashboard
notice and are not presented as connected camera telemetry. Camera credentials
are not persisted by the Phase 1 add-camera form.

## Build

The project targets Android API 34, uses Java/Kotlin 17 in CI, and builds with:

```bash
./gradlew assembleDebug
```

The APK is uploaded by `.github/workflows/build-apk.yml` under the
`SmartCam-AI-debug` artifact.

## Privacy Direction

Monitoring is opt-in and visible. The architecture reserves space for consent,
retention cleanup, audit logs, role permissions, notification cooldowns, and
local processing. Identity recognition is not implemented or enabled.
