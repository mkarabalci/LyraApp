package com.turkcell.lyraapp.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turkcell.lyraapp.data.search.SearchRepository
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
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(SearchContract.State())
    val state: StateFlow<SearchContract.State> = _state.asStateFlow()

    private val _effect = Channel<SearchContract.Effect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    init {
        loadGenres()
    }

    fun onIntent(intent: SearchContract.Intent) {
        when (intent) {
            is SearchContract.Intent.QueryChanged -> _state.update { it.copy(query = intent.value) }
            is SearchContract.Intent.GenreFilterSelected -> applyFilter(intent.genreId)
            is SearchContract.Intent.GenreCardClicked -> sendEffect(SearchContract.Effect.NavigateToGenreDetail(intent.genreId))
        }
    }

    private fun loadGenres() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            searchRepository.getGenres()
                .onSuccess { genres ->
                    _state.update { it.copy(allGenres = genres, filteredGenres = genres) }
                }
                .onFailure { sendEffect(SearchContract.Effect.ShowError(it.message ?: "Bilinmeyen hata")) }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun applyFilter(genreId: String?) {
        _state.update { current ->
            val filtered = if (genreId == null) current.allGenres
                          else current.allGenres.filter { it.id == genreId }
            current.copy(selectedGenreId = genreId, filteredGenres = filtered)
        }
    }

    private fun sendEffect(effect: SearchContract.Effect) {
        viewModelScope.launch { _effect.send(effect) }
    }
}
