## mvi-contracts.md

> XxxContract nesnesi içindeki State, Intent ve Effect sınıflarına ait kodlama kuralları.
> Referans implementasyon: `LoginContract.kt`

---

### Genel Şablon

```kotlin
object XxxContract {

    data class State(
        // görsel/ekran durumu alanları
        val isLoading: Boolean = false,
        val isFormEnabled: Boolean = false,   // türetilmiş alan — ViewModel hesaplar
    )

    sealed class Intent {
        data class FieldChanged(val value: String) : Intent()
        object ButtonClicked : Intent()
    }

    sealed class Effect {
        object NavigateToHome : Effect()
        data class ShowError(val message: String) : Effect()
    }
}
```

---

### State Kuralları

1. **Yalnızca görsel/ekran durumunu taşır.** İş mantığı verisi ViewModel'e aittir.

2. **`error: String?` gibi hata alanı State'e kesinlikle eklenmez.**
   Hatalar `Effect.ShowError` ile iletilir.
   ```kotlin
   // YANLIS
   data class State(val error: String? = null)

   // DOGRU
   sealed class Effect {
       data class ShowError(val message: String) : Effect()
   }
   ```

3. **Türetilmiş (derived) durum alanları kabul edilir.**
   `isFormEnabled`, `isLoginEnabled` gibi alanlar State'e dahil edilir ve ViewModel tarafından `updateForm()` içinde hesaplanarak yazılır. Ekran bu alanı doğrudan okur — kendi içinde hesaplamaz.

4. **Tüm alanlar varsayılan değer alır** — Preview ve birim test kolaylığı için.

5. **`isLoading: Boolean = false` her State'te bulunur.**
   Asenkron işlem sırasında buton devre dışı bırakma ve yükleme göstergesi için zorunludur.

---

### Intent Kuralları

1. Her Intent bir kullanıcı aksiyonunu temsil eder.
2. Veri taşıyanlar `data class`, taşımayanlar `object` olur.
3. Intent sınıfları iş mantığı içermez — yalnızca veri taşır.

---

### Effect Kuralları

1. Tek seferlik yan etkileri temsil eder; composable yeniden oluşturulduğunda tekrar tetiklenmez.
2. `Channel.BUFFERED` ile gönderilir, `receiveAsFlow()` ile tüketilir.
3. Her feature'ın Effect'inde `ShowError(message: String)` bulunmak zorundadır.
4. `NavigateToXxx` effect'leri hedef ekran hazır olana kadar Snackbar'a bağlanır; boş bırakılmaz.
