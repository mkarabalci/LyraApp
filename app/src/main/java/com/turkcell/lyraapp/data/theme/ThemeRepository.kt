package com.turkcell.lyraapp.data.theme

import kotlinx.coroutines.flow.StateFlow

interface ThemeRepository {
    val isDark: StateFlow<Boolean>
    suspend fun toggle()
}
