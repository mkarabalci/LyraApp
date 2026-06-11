package com.turkcell.lyraapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = Channel<HomeContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        _state.update {
            it.copy(
                greeting = computeGreeting(),
                quickPlayPlaylists = buildQuickPlaylists(),
                recentlyPlayed = buildRecentlyPlayed(),
                recommendedPlaylists = buildRecommended(),
            )
        }
    }

    fun onIntent(intent: HomeContract.Intent) {
        when (intent) {
            is HomeContract.Intent.TabSelected ->
                _state.update { it.copy(selectedTab = intent.tab) }
        }
    }

    private fun computeGreeting(): String = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 5..11  -> "Günaydın"
        in 12..17 -> "İyi günler"
        in 18..21 -> "İyi akşamlar"
        else      -> "İyi geceler"
    }

    private fun buildQuickPlaylists() = listOf(
        HomeContract.PlaylistItem("qp1", "Türkçe Pop",   0xFF7B5EA7L),
        HomeContract.PlaylistItem("qp2", "Uzun yol", 0xFF6466C5L),
        HomeContract.PlaylistItem("qp3", "Arabada kopmalık", 0xFFB5852AL),
        HomeContract.PlaylistItem("qp4", "Ders çalışırken",       0xFF2E9B8AL),
        HomeContract.PlaylistItem("qp5", "Gece Modu",    0xFF4E8B5FL),
        HomeContract.PlaylistItem("qp6", "Arabesk",   0xFF3B89A0L),
    )

    private fun buildRecentlyPlayed() = listOf(
        HomeContract.TrackItem("rp1", "Türkçe Pop", "Aya Benzer", 0xFFB5852AL),
        HomeContract.TrackItem("rp2", "Uzun Yol",    "Gidiyorum",        0xFF4E8B5FL),
        HomeContract.TrackItem("rp3", "Arabada kopmalık",   "Burada Sokaklar",        0xFF2E9B8AL),
        HomeContract.TrackItem("rp4", "Gece Modu",   "Gül Beyaz Gül",        0xFF7B5EA7L),
    )

    private fun buildRecommended() = listOf(
        HomeContract.PlaylistItem("rc1", "Akşam Rüzgarı",  0xFF6B5B9CL),
        HomeContract.PlaylistItem("rc2", "Haftalık Keşif",     0xFF4B5FA6L),
        HomeContract.PlaylistItem("rc3", "Türkçe Top-50",   0xFF3A7D44L),
        HomeContract.PlaylistItem("rc4", "Yaz Akşamları", 0xFFB5852AL),
    )

    private fun sendEffect(effect: HomeContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
