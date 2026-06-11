package com.turkcell.lyraapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.lyraapp.data.home.HomeRepository
import com.turkcell.lyraapp.data.home.PlaylistData
import com.turkcell.lyraapp.data.home.TrackData
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
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeContract.State())
    val state: StateFlow<HomeContract.State> = _state.asStateFlow()

    private val _effect = Channel<HomeContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        _state.update { it.copy(greeting = computeGreeting(), isLoading = true) }
        loadHomeData()
    }

    fun onIntent(intent: HomeContract.Intent) {

    }

    private fun loadHomeData() {
        viewModelScope.launch {
            val quickPlay = homeRepository.getQuickPlayPlaylists()
            val recentlyPlayed = homeRepository.getRecentlyPlayed()
            val recommended = homeRepository.getRecommendedPlaylists()

            val error = listOf(quickPlay, recentlyPlayed, recommended)
                .firstNotNullOfOrNull { it.exceptionOrNull() }

            if (error != null) {
                sendEffect(HomeContract.Effect.ShowError(error.message ?: "Bilinmeyen hata"))
            } else {
                _state.update {
                    it.copy(
                        quickPlayPlaylists = quickPlay.getOrDefault(emptyList()).map { d -> d.toUi() },
                        recentlyPlayed = recentlyPlayed.getOrDefault(emptyList()).map { d -> d.toUi() },
                        recommendedPlaylists = recommended.getOrDefault(emptyList()).map { d -> d.toUi() },
                    )
                }
            }

            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun computeGreeting(): String = when (Calendar.getInstance().get(Calendar.HOUR_OF_DAY)) {
        in 5..11  -> "Günaydın"
        in 12..17 -> "İyi günler"
        in 18..21 -> "İyi akşamlar"
        else      -> "İyi geceler"
    }

    private fun PlaylistData.toUi() = HomeContract.PlaylistItem(id, name, colorArgb)

    private fun TrackData.toUi() = HomeContract.TrackItem(id, title, artist, colorArgb)

    private fun sendEffect(effect: HomeContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
