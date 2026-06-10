package com.turkcell.lyraapp.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.turkcell.lyraapp.ui.theme.LyraAppTheme

@Composable
fun RegisterScreen(
    state: RegisterContract.State,
    onIntent: (RegisterContract.Intent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .imePadding(),
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            RegisterBackButton(
                onBackClick = { onIntent(RegisterContract.Intent.NavigateToLoginClicked) },
            )

            Spacer(modifier = Modifier.height(24.dp))

            RegisterHeaderTexts()

            Spacer(modifier = Modifier.height(32.dp))

            RegisterNameRow(
                firstName = state.firstName,
                lastName = state.lastName,
                onFirstNameChange = { onIntent(RegisterContract.Intent.FirstNameChanged(it)) },
                onLastNameChange = { onIntent(RegisterContract.Intent.LastNameChanged(it)) },
            )

            Spacer(modifier = Modifier.height(12.dp))

            RegisterPhoneField(
                phone = state.phone,
                onPhoneChange = { onIntent(RegisterContract.Intent.PhoneChanged(it)) },
            )

            Spacer(modifier = Modifier.height(12.dp))

            RegisterPasswordField(
                password = state.password,
                passwordVisible = state.passwordVisible,
                onPasswordChange = { onIntent(RegisterContract.Intent.PasswordChanged(it)) },
                onPasswordVisibilityToggle = { onIntent(RegisterContract.Intent.TogglePasswordVisibility) },
            )

            Spacer(modifier = Modifier.height(8.dp))

            RegisterPasswordStrengthIndicator(strength = state.passwordStrength)

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "En az 8 karakter, bir rakam icermeli.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(modifier = Modifier.height(16.dp))

            RegisterTermsRow(
                accepted = state.termsAccepted,
                onToggle = { onIntent(RegisterContract.Intent.TermsToggled) },
            )

            Spacer(modifier = Modifier.height(24.dp))

            RegisterPrimaryButton(
                isLoading = state.isLoading,
                isFormEnabled = state.isFormEnabled,
                onRegisterClick = { onIntent(RegisterContract.Intent.RegisterClicked) },
            )

            Spacer(modifier = Modifier.height(24.dp))

            RegisterLoginRow(
                onLoginClick = { onIntent(RegisterContract.Intent.NavigateToLoginClicked) },
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun RegisterBackButton(onBackClick: () -> Unit) {
    IconButton(
        onClick = onBackClick,
        modifier = Modifier.size(40.dp),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Geri",
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun RegisterHeaderTexts() {
    Text(
        text = "Hesap olustur",
        style = MaterialTheme.typography.headlineLarge,
        color = MaterialTheme.colorScheme.onSurface,
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Birkac adimda Lyra'ya katil ve calma listeni olustur.",
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Composable
private fun RegisterNameRow(
    firstName: String,
    lastName: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        OutlinedTextField(
            value = firstName,
            onValueChange = onFirstNameChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Ad") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
        )
        OutlinedTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Soyad") },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
        )
    }
}

@Composable
private fun RegisterPhoneField(
    phone: String,
    onPhoneChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = phone,
        onValueChange = onPhoneChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text("Telefon numarasi") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.PhoneAndroid,
                contentDescription = null,
            )
        },
        prefix = { Text("+90  ") },
        placeholder = { Text("5XX XXX XX XX") },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
    )
}

@Composable
private fun RegisterPasswordField(
    password: String,
    passwordVisible: Boolean,
    onPasswordChange: (String) -> Unit,
    onPasswordVisibilityToggle: () -> Unit,
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Sifre") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
            )
        },
        trailingIcon = {
            IconButton(onClick = onPasswordVisibilityToggle) {
                Icon(
                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = null,
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
    )
}

@Composable
private fun RegisterPasswordStrengthIndicator(strength: Int) {
    val strengthColor = when (strength) {
        1 -> MaterialTheme.colorScheme.error
        2 -> MaterialTheme.colorScheme.tertiary
        3 -> Color(0xFF4CAF50)
        else -> MaterialTheme.colorScheme.outlineVariant
    }
    val trackColor = MaterialTheme.colorScheme.outlineVariant

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(3) { index ->
            LinearProgressIndicator(
                progress = { if (strength > index) 1f else 0f },
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp),
                color = strengthColor,
                trackColor = trackColor,
                strokeCap = StrokeCap.Round,
            )
        }
    }
}

@Composable
private fun RegisterTermsRow(
    accepted: Boolean,
    onToggle: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Checkbox(
            checked = accepted,
            onCheckedChange = { onToggle() },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
            ),
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)) {
                    append("Kullanim Kosullari")
                }
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                    append(" ve ")
                }
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)) {
                    append("Gizlilik Politikasi")
                }
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                    append("'ni okudum, kabul ediyorum.")
                }
            },
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun RegisterPrimaryButton(
    isLoading: Boolean,
    isFormEnabled: Boolean,
    onRegisterClick: () -> Unit,
) {
    Button(
        onClick = onRegisterClick,
        enabled = isFormEnabled && !isLoading,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(50),
    ) {
        Text(
            text = "Kayit ol ->",
            style = MaterialTheme.typography.labelLarge,
        )
    }
}

@Composable
private fun RegisterLoginRow(onLoginClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Zaten hesabin var mi? ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        TextButton(
            onClick = onLoginClick,
            contentPadding = PaddingValues(0.dp),
        ) {
            Text(
                text = "Giris yap",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreviewLight() {
    LyraAppTheme(darkTheme = false) {
        RegisterScreen(
            state = RegisterContract.State(),
            onIntent = {},
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreviewDark() {
    LyraAppTheme(darkTheme = true) {
        RegisterScreen(
            state = RegisterContract.State(
                passwordStrength = 2,
                termsAccepted = true,
            ),
            onIntent = {},
        )
    }
}
