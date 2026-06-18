package com.turkcell.lyraapp.data.theme

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class MockThemeRepository @Inject constructor() : ThemeRepository {

    private val _isDark = MutableStateFlow(false)
    override val isDark: StateFlow<Boolean> = _isDark.asStateFlow()

    override suspend fun toggle() {
        _isDark.update { !it }
    }
}
