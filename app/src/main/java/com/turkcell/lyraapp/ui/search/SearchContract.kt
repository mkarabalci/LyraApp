package com.turkcell.lyraapp.ui.search

import com.turkcell.lyraapp.data.search.Genre

object SearchContract {

    data class State(
        val query: String = "",
        val selectedGenreId: String? = null,
        val allGenres: List<Genre> = emptyList(),
        val filteredGenres: List<Genre> = emptyList(),
        val isLoading: Boolean = false,
    )

    sealed class Intent {
        data class QueryChanged(val value: String) : Intent()
        data class GenreFilterSelected(val genreId: String?) : Intent()
        data class GenreCardClicked(val genreId: String) : Intent()
    }

    sealed class Effect {
        data class NavigateToGenreDetail(val genreId: String) : Effect()
        data class ShowError(val message: String) : Effect()
    }
}
