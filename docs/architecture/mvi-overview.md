## mvi-overview.md

> MVI mimarisindeki genel prensipleri, veri akışını, katman sorumluluklarını ilgilendiren bütün kurallar burada tanımlanmaktadır.

---

### Veri Akışı

```
Kullanıcı Aksiyonu
       │
       ▼
Screen (stateless) — onIntent(Intent) çağrısı
       │
       ▼
ViewModel.onIntent()
       │
       ├── _state.update()  ──► StateFlow ──► Screen (recompose)
       │
       ├── _effect.send()  ──► Channel<Effect> ──► Route (LaunchedEffect)
       │                                                   │
       │                                        Navigasyon / Snackbar
       │
       └── viewModelScope.launch()
                    │
                    ▼
              Repository (suspend)
                    │
                    ▼
                Result<T>
                    │
                    ├── onSuccess ──► _effect.send(NavigateToX)
                    └── onFailure ──► _effect.send(ShowError)

```

- **State**: Sürekli gözlemlenen, immutable, tekil durum. `StateFlow` ile yayılır.
- **Effect**: Bir kez tüketilen olay (navigasyon, snackbar). `Channel` + `receiveAsFlow` ile yayılır; **asla State içinde tutulmaz**.
- **Intent**: UI'dan ViewModel'e giden tek niyet kanalı. Tek giriş noktası `onIntent(...)`.

---

### Katman Sorumlulukları

| Katman | Dosya | Sorumluluk |
|---|---|---|
| Contract | `XxxContract.kt` | State / Intent / Effect tip tanımları |
| ViewModel | `XxxViewModel.kt` | İş mantığı, state yönetimi, effect üretimi |
| Route | `XxxRoute.kt` | ViewModel bağlantısı, effect dinleme, Scaffold kapsayıcı |
| Screen | `XxxScreen.kt` | Saf UI — ViewModel ve navigasyon bilmez |

---

### Route Kuralları

- `hiltViewModel()` ile ViewModel edinir.
- `collectAsStateWithLifecycle()` ile state okur.
- `LaunchedEffect(Unit)` bloğu içinde `viewModel.effect.collect {}` yan etkileri dinler.
- `Scaffold` + `SnackbarHost` kapsayıcısını barındırır.
- **Scaffold'ın `paddingValues` parametresi `Modifier.padding(paddingValues)` olarak Screen'e iletilmek zorunludur.**

```kotlin
Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) { paddingValues ->
    XxxScreen(
        state = state,
        onIntent = viewModel::onIntent,
        modifier = Modifier.padding(paddingValues),
    )
}
```

---

### Screen Kuralları

- Parametre listesi: `state: XxxContract.State`, `onIntent: (XxxContract.Intent) -> Unit`, `modifier: Modifier = Modifier`.
- Başka hiçbir bağımlılık (ViewModel, NavController, Context) alamaz.
- Kök `Column` modifiyerine `.imePadding()` eklenir — klavye açıldığında içerik kaymasını önler.

```kotlin
Column(
    modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 24.dp)
        .imePadding(),
)
```

- `modifier` parametresi kök kapsayıcıya uygulanır: `modifier = modifier.fillMaxSize()`.

---

### NavHost Entegrasyonu

Her ekran `MainActivity` içindeki `NavHost`'a bir `composable()` olarak kaydedilir.
Route, yalnızca navigasyon yapılacaksa `NavController` parametresi alır:

```kotlin
NavHost(navController = navController, startDestination = "login") {
    composable("login") { LoginRoute() }
    composable("home")  { HomeRoute(navController) }
}
```

Navigasyon effect'i (`NavigateToXxx`) hedef ekran tamamlanana kadar Snackbar/log'a bağlanır; asla boş bırakılmaz.

---

### Paket ve Dosya Yapısı

Bir `<Feature>` / `<Screen>` için yerleşim:

```
com.turkcell.lyraapp/
├── ui/<feature>/<screen>/
│   ├── <Screen>Contract.kt   // UiState + Intent + Effect (tek dosya)
│   ├── <Screen>ViewModel.kt  // @HiltViewModel
│   ├── <Screen>Route.kt      // stateful — ViewModel bağlantısı, Effect dinleme
│   └── <Screen>Screen.kt     // stateless — saf UI
├── data/<feature>/
│   ├── <X>Repository.kt      // interface
│   └── <Impl>Repository.kt   // implementasyon (@Inject constructor)
└── di/
    └── <X>Module.kt          // @Module @InstallIn(SingletonComponent) @Binds
```
