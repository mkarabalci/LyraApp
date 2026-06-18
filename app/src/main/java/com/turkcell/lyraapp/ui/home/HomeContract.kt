package com.turkcell.lyraapp.ui.home

object HomeContract {

    data class PlaylistItem(
        val id: String,
        val name: String,
        val colorArgb: Long,
    )

    data class TrackItem(
        val id: String,
        val title: String,
        val artist: String,
        val colorArgb: Long,
    )

    data class State(
        val isLoading: Boolean = false,
        val greeting: String = "",
        val userInitials: String = "ZK",
        val isDarkMode: Boolean = false,
        val quickPlayPlaylists: List<PlaylistItem> = emptyList(),
        val recentlyPlayed: List<TrackItem> = emptyList(),
        val recommendedPlaylists: List<PlaylistItem> = emptyList(),
    )

    sealed class Intent {
        object DarkModeToggled : Intent()
    }

    sealed class Effect {
        data class ShowError(val message: String) : Effect()
    }
}
