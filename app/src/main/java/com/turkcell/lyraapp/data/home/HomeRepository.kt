package com.turkcell.lyraapp.data.home

data class PlaylistData(
    val id: String,
    val name: String,
    val colorArgb: Long,
)

data class TrackData(
    val id: String,
    val title: String,
    val artist: String,
    val colorArgb: Long,
)

interface HomeRepository {
    suspend fun getQuickPlayPlaylists(): Result<List<PlaylistData>>
    suspend fun getRecentlyPlayed(): Result<List<TrackData>>
    suspend fun getRecommendedPlaylists(): Result<List<PlaylistData>>
}
