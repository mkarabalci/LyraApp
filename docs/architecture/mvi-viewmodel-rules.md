## mvi-viewmodel-rules.md

> @HiltViewModel sınıflarına ait kodlama kuralları.
> Referans implementasyon: `LoginViewModel.kt`

---

### Genel Şablon

```kotlin
@HiltViewModel
class XxxViewModel @Inject constructor(
    private val xxxRepository: XxxRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(XxxContract.State())
    val state: StateFlow<XxxContract.State> = _state.asStateFlow()

    private val _effect = Channel<XxxContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: XxxContract.Intent) {
        when (intent) {
            is XxxContract.Intent.FieldChanged  -> updateForm { it.copy(field = intent.value) }
            is XxxContract.Intent.ToggleXxx     -> _state.update { it.copy(xxxVisible = !it.xxxVisible) }
            is XxxContract.Intent.ButtonClicked -> performAction()
            is XxxContract.Intent.NavigationIntent -> sendEffect(XxxContract.Effect.NavigateToYyy)
        }
    }

    // Form alanı güncellemeleri için zorunlu yardımcı
    private fun updateForm(transform: (XxxContract.State) -> XxxContract.State) {
        _state.update { current ->
            val updated = transform(current)
            updated.copy(isFormEnabled = updated.isFormValid())
        }
    }

    private fun XxxContract.State.isFormValid(): Boolean =
        field1.isNotBlank() && field2.isNotBlank()

    private fun performAction() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            xxxRepository.action(_state.value.field1, _state.value.field2)
                .onSuccess { sendEffect(XxxContract.Effect.NavigateToHome) }
                .onFailure { sendEffect(XxxContract.Effect.ShowError(it.message ?: "Bilinmeyen hata")) }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun sendEffect(effect: XxxContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
```

---

### updateForm() Kuralı

Form alanı değiştiren her Intent (`FieldChanged`, `PasswordChanged` vb.) **doğrudan `_state.update {}` kullanmaz**; `updateForm()` özel yardımcısı zorunludur.

**Neden:** State güncellemesi ve türetilmiş alan (`isFormEnabled`) hesabı atomik olarak gerçekleşir. Validasyon mantığı `onIntent` dallarına dağılmaz.

Form dışı intentler (Toggle, navigasyon) için `updateForm()` kullanılmaz; doğrudan `_state.update {}` yeterlidir.

---

### Hata Yönetimi Kuralı

`error: String?` State'e eklenmez. Hatalar Effect kanalıyla iletilir:

```kotlin
result
    .onSuccess { sendEffect(Effect.NavigateToHome) }
    .onFailure { sendEffect(Effect.ShowError(it.message ?: "Bilinmeyen hata")) }
```

---

### Yükleme Durumu Kuralı

```kotlin
_state.update { it.copy(isLoading = true) }
// ... asenkron işlem ...
_state.update { it.copy(isLoading = false) }
```

Screen tarafında buton koşulu: `enabled = state.isFormEnabled && !state.isLoading`.

---

### Çift Gönderim Koruması

Asenkron işlem başlatmadan önce erken return ile korunur:

```kotlin
private fun performAction() {
    val state = _state.value
    if (!state.isFormEnabled || state.isLoading) return
    viewModelScope.launch { ... }
}
```

---

### isFormValid() Kuralı

- `State` üzerinde private extension function olarak tanımlanır.
- Yalnızca ViewModel içinde kullanılır.
- Boş alan kontrolü için `isNotBlank()` kullanılır (`isEmpty()` değil).
