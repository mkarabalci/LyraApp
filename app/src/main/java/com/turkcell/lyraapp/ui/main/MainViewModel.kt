package com.turkcell.lyraapp.ui.main

import androidx.lifecycle.ViewModel
import com.turkcell.lyraapp.data.theme.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    themeRepository: ThemeRepository,
) : ViewModel() {

    val isDarkTheme: StateFlow<Boolean> = themeRepository.isDark
}
