#d decisions.md

>Projede verilen bütün mimarisel-teknik kararları ve karar geçmişini içeren dökümantasyondur.

---

### Dependency Injection Kütüphanesi

- Seçim*: **Hilt**

- Son Güncelleme Tarihi*: 04.06.2026

- Alternatifler: **Koin**

- Sebep: **Opsiyonel**


### Navigasyon

- Seçim: **Compose Navigation**

- Son Güncelleme Tarihi: 04.06.2026

### Material Icons Extended

- Seçim: **androidx.compose.material:material-icons-extended**

- Son Güncelleme Tarihi: 10.06.2026

- Alternatifler: Harici SVG dosyaları, custom `ImageVector` tanımları

- Sebep: Login ekranında kullanılan `PhoneAndroid` ve `GraphicEq` ikonları, varsayılan (`material-icons-core`) ikon setinde yer almamaktadır. Extended set, Compose BOM tarafından yönetildiği için ek versiyon takibi gerektirmez.

---

### Mimari: MVI (Model-View-Intent)

- Seçim: **LoginContract (State + Intent + Effect) + LoginViewModel (@HiltViewModel) + LoginRoute / LoginScreen ayrımı**

- Son Güncelleme Tarihi: 10.06.2026

- Sebep: Tek yönlü veri akışı, test edilebilirlik, yan etkilerin (navigasyon, snackbar) UI katmanından ayrılması. `LoginContract` nesnesi State / Intent / Effect'i tek dosyada tutar.

- Notlar: `LoginRoute` stateful kapsayıcıdır (ViewModel, NavController). `LoginScreen` saf fonksiyondur; ViewModel ve navigasyon bilmez.

---

### Annotation Processor

- Seçim: **KSP** (`com.google.devtools.ksp`)

- Son Güncelleme Tarihi: 10.06.2026

- Sürüm: `2.2.10-2.0.2` — Kotlin sürümüyle birebir eşleşme zorunludur.

- Alternatifler: KAPT

- Sebep: KAPT Kotlin K2 derleyicisiyle kısıtlı desteklidir ve deprecated sürecine girmiştir. KSP ~2x daha hızlıdır.

---

### Doğrulanmış Bağımlılık Sürümleri (10.06.2026)

| Bağımlılık | Sürüm |
|---|---|
| `hilt` | `2.59.2` |
| `ksp` | `2.2.10-2.0.2` |
| `hilt-navigation-compose` | `1.3.0` |
| `lifecycle-runtime-compose` | `2.10.0` |
| `kotlinx-coroutines-android` | `1.11.0` |
| `navigation-compose` | `2.9.5` |

---

### AGP 9 + KSP Uyumluluk Notu

- Seçim: `android.disallowKotlinSourceSets=false` (`gradle.properties`)

- Son Güncelleme Tarihi: 10.06.2026

- Sebep: AGP 9.x "built-in Kotlin" özelliği, KSP'nin ürettiği kaynak dizinlerini `kotlin.sourceSets` DSL ile eklemesine izin vermez. Bu bayrak AGP'nin kendi önerisidir; KSP'nin AGP 9.x resmi desteği iyileştikçe kaldırılabilir.