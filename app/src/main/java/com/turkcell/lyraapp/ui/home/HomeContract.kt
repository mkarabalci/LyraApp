package com.turkcell.lyraapp.ui.home

object HomeContract {

    enum class HomeTab { AnaSayfa, Ara, Kutuphane, Favoriler, Profil }

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
        val quickPlayPlaylists: List<PlaylistItem> = emptyList(),
        val recentlyPlayed: List<TrackItem> = emptyList(),
        val recommendedPlaylists: List<PlaylistItem> = emptyList(),
        val selectedTab: HomeTab = HomeTab.AnaSayfa,
    )

    sealed class Intent {
        data class TabSelected(val tab: HomeTab) : Intent()
    }

    sealed class Effect {
        data class ShowError(val message: String) : Effect()
    }
}
