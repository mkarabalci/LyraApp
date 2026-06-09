# LyraApp - Tipografi Sistemi

> Bu dosya LyraApp isimli uygulamanin tipografi paleti icin
> **tek dogruluk kaynagindir** (single source of truth) ve
> dogrudan bir **Android Jetpack Compose** projesinde kullanilmak
> uzere duzenlenmistir.

---

## 1. Temel Kural

> Hicbir `@Composable` icinde ham `TextStyle(...)` tanimi yapilmaz.
>
> Tipografi daima `MaterialTheme.typography.<slot>` uzerinden okunmak zorundadir.

Ham `TextStyle(...)` tanimi yalnizca `Type.kt` icinde, `LyraTypography` degiskeni tanimlanirken kullanilir.

---

## 2. Font Ailesi

**Font:** Roboto

Roboto, Android platformunun varsayilan fontudur. `FontFamily.Default`, tum Android cihazlarda Roboto'ya eslenir. Harici bir bagimlilik veya font dosyasi gerekmez; platform garantisi esas alinir.

```kotlin
private val Roboto = FontFamily.Default
```

---

## 3. `Type.kt` — Tam Scale Tablosu

Asagidaki degerler **Material Design 3 spesifikasyonuna** birebir uymaktadir.

| Slot            | Boyut  | Line Height | Letter Spacing | Weight        |
|-----------------|--------|-------------|----------------|---------------|
| displayLarge    | 57 sp  | 64 sp       | -0.25 sp       | Regular (400) |
| displayMedium   | 45 sp  | 52 sp       |  0.00 sp       | Regular (400) |
| displaySmall    | 36 sp  | 44 sp       |  0.00 sp       | Regular (400) |
| headlineLarge   | 32 sp  | 40 sp       |  0.00 sp       | Regular (400) |
| headlineMedium  | 28 sp  | 36 sp       |  0.00 sp       | Regular (400) |
| headlineSmall   | 24 sp  | 32 sp       |  0.00 sp       | Regular (400) |
| titleLarge      | 22 sp  | 28 sp       |  0.00 sp       | Regular (400) |
| titleMedium     | 16 sp  | 24 sp       |  0.15 sp       | Medium  (500) |
| titleSmall      | 14 sp  | 20 sp       |  0.10 sp       | Medium  (500) |
| bodyLarge       | 16 sp  | 24 sp       |  0.50 sp       | Regular (400) |
| bodyMedium      | 14 sp  | 20 sp       |  0.25 sp       | Regular (400) |
| bodySmall       | 12 sp  | 16 sp       |  0.40 sp       | Regular (400) |
| labelLarge      | 14 sp  | 20 sp       |  0.10 sp       | Medium  (500) |
| labelMedium     | 12 sp  | 16 sp       |  0.50 sp       | Medium  (500) |
| labelSmall      | 11 sp  | 16 sp       |  0.50 sp       | Medium  (500) |

---

## 4. `Theme.kt` — Entegrasyon

`LyraTypography`, `LyraAppTheme` icinde `MaterialTheme`'e asagidaki sekilde aktarilir:

```kotlin
MaterialTheme(
    colorScheme = colorScheme,
    typography  = LyraTypography,
    content     = content,
)
```

---

## 5. Kullanim Ornekleri

```kotlin
// Baslik gostermek icin
Text(
    text  = "Sarki Adi",
    style = MaterialTheme.typography.titleLarge,
)

// Alt aciklama icin
Text(
    text  = "Sanatci Adi",
    style = MaterialTheme.typography.bodyMedium,
)

// Etiket icin
Text(
    text  = "3:45",
    style = MaterialTheme.typography.labelSmall,
)
```

---

## 6. Sikca Yapilan Hatalar

| Hata | Dogru Kullanim |
|------|----------------|
| `style = TextStyle(fontSize = 16.sp)` | `style = MaterialTheme.typography.bodyLarge` |
| `fontFamily = FontFamily.Default` dogrudan Composable icinde | `Type.kt` icinde tanimla, `MaterialTheme.typography` uzerinden oku |
| Yeni bir slot icin `Color.kt`'ye benzer sekilde ayri degisken tanimlamak | Her zaman `LyraTypography` uzerindeki mevcut slotlari kullan |
