package com.turkcell.lyraapp.ui.login

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
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginContract.State())
    val state: StateFlow<LoginContract.State> = _state.asStateFlow()

    private val _effect = Channel<LoginContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onIntent(intent: LoginContract.Intent) {
        when (intent) {
            is LoginContract.Intent.PhoneChanged ->
                updateForm { it.copy(phone = intent.value) }
            is LoginContract.Intent.PasswordChanged ->
                updateForm { it.copy(password = intent.value) }
            is LoginContract.Intent.TogglePasswordVisibility ->
                _state.update { it.copy(passwordVisible = !it.passwordVisible) }
            is LoginContract.Intent.LoginClicked -> login()
            is LoginContract.Intent.ForgotPasswordClicked ->
                sendEffect(LoginContract.Effect.NavigateToForgotPassword)
            is LoginContract.Intent.RegisterClicked ->
                sendEffect(LoginContract.Effect.NavigateToRegister)
        }
    }

    private fun updateForm(transform: (LoginContract.State) -> LoginContract.State) {
        _state.update { current ->
            val updated = transform(current)
            updated.copy(isLoginEnabled = updated.isFormValid())
        }
    }
    private fun login() {
        val state = _state.value
        if (!state.isLoginEnabled || state.isLoading) return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            authRepository.login(_state.value.phone, _state.value.password)
                .onSuccess { sendEffect(LoginContract.Effect.NavigateToHome) }
                .onFailure { sendEffect(LoginContract.Effect.ShowError(it.message ?: "Bilinmeyen hata")) }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun LoginContract.State.isFormValid(): Boolean =
        phone.isNotBlank() && password.isNotBlank()

    private fun sendEffect(effect: LoginContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
