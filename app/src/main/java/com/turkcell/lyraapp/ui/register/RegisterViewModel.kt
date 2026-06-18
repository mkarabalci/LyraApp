package com.turkcell.lyraapp.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.lyraapp.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterContract.State())
    val state: StateFlow<RegisterContract.State> = _state.asStateFlow()

    private val _effect = Channel<RegisterContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: RegisterContract.Intent) {
        when (intent) {
            is RegisterContract.Intent.FirstNameChanged ->
                updateForm { it.copy(firstName = intent.value) }
            is RegisterContract.Intent.LastNameChanged ->
                updateForm { it.copy(lastName = intent.value) }
            is RegisterContract.Intent.PhoneChanged ->
                updateForm { it.copy(phone = intent.value) }
            is RegisterContract.Intent.PasswordChanged ->
                updateForm { it.copy(password = intent.value, passwordStrength = computeStrength(intent.value)) }
            is RegisterContract.Intent.TogglePasswordVisibility ->
                _state.update { it.copy(passwordVisible = !it.passwordVisible) }
            is RegisterContract.Intent.TermsToggled ->
                updateForm { it.copy(termsAccepted = !it.termsAccepted) }
            is RegisterContract.Intent.RegisterClicked -> register()
            is RegisterContract.Intent.NavigateToLoginClicked ->
                sendEffect(RegisterContract.Effect.NavigateToLogin)
        }
    }

    private fun updateForm(transform: (RegisterContract.State) -> RegisterContract.State) {
        _state.update { current ->
            val updated = transform(current)
            updated.copy(isFormEnabled = updated.isFormValid())
        }
    }

    private fun RegisterContract.State.isFormValid(): Boolean =
        firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            phone.isNotBlank() &&
            password.length >= 8 &&
            password.any { it.isDigit() } &&
            termsAccepted

    private fun computeStrength(password: String): Int = when {
        password.isEmpty() -> 0
        password.length < 4 -> 1
        password.length < 8 || !password.any { it.isDigit() } -> 2
        else -> 3
    }

    private fun register() {
        val state = _state.value
        if (!state.isFormEnabled || state.isLoading) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            authRepository.register(
                firstName = state.firstName,
                lastName = state.lastName,
                phone = state.phone,
                password = state.password,
            )
                .onSuccess { sendEffect(RegisterContract.Effect.NavigateToHome) }
                .onFailure { sendEffect(RegisterContract.Effect.ShowError(it.message ?: "Bilinmeyen hata")) }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun sendEffect(effect: RegisterContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
