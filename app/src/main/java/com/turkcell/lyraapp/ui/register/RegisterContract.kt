package com.turkcell.lyraapp.ui.register

object RegisterContract {

    data class State(
        val firstName: String = "",
        val lastName: String = "",
        val phone: String = "",
        val password: String = "",
        val passwordVisible: Boolean = false,
        val termsAccepted: Boolean = false,
        val passwordStrength: Int = 0,
        val isLoading: Boolean = false,
        val isFormEnabled: Boolean = false,
    )

    sealed class Intent {
        data class FirstNameChanged(val value: String) : Intent()
        data class LastNameChanged(val value: String) : Intent()
        data class PhoneChanged(val value: String) : Intent()
        data class PasswordChanged(val value: String) : Intent()
        object TogglePasswordVisibility : Intent()
        object TermsToggled : Intent()
        object RegisterClicked : Intent()
        object NavigateToLoginClicked : Intent()
    }

    sealed class Effect {
        object NavigateToLogin : Effect()
        object NavigateToHome : Effect()
        data class ShowError(val message: String) : Effect()
    }
}
