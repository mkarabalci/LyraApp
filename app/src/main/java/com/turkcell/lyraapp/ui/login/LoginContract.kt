package com.turkcell.lyraapp.ui.login

object LoginContract {

    data class State(
        val phone: String = "",
        val password: String = "",
        val passwordVisible: Boolean = false,
        val isLoading: Boolean = false,
        val isLoginEnabled: Boolean = false,
    )
    
    sealed class Intent {
        data class PhoneChanged(val value: String) : Intent()
        data class PasswordChanged(val value: String) : Intent()
        object TogglePasswordVisibility : Intent()
        object LoginClicked : Intent()
        object ForgotPasswordClicked : Intent()
        object RegisterClicked : Intent()
    }

    sealed class Effect {
        object NavigateToHome : Effect()
        object NavigateToForgotPassword : Effect()
        object NavigateToRegister : Effect()
        data class ShowError(val message: String) : Effect()
    }
}
