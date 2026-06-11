package com.turkcell.lyraapp.data.home

import javax.inject.Inject

class MockHomeRepository @Inject constructor() : HomeRepository {

    override suspend fun getQuickPlayPlaylists(): Result<List<PlaylistData>> =
        Result.success(QUICK_PLAY_PLAYLISTS)

    override suspend fun getRecentlyPlayed(): Result<List<TrackData>> =
        Result.success(RECENTLY_PLAYED)

    override suspend fun getRecommendedPlaylists(): Result<List<PlaylistData>> =
        Result.success(RECOMMENDED_PLAYLISTS)

    private companion object {

        val QUICK_PLAY_PLAYLISTS = listOf(
            PlaylistData("qp1", "Gece Sürüşü",   0xFF7B5EA7L),
            PlaylistData("qp2", "Sabah Kahvesi", 0xFF6466C5L),
            PlaylistData("qp3", "Neon Sokaklar", 0xFFB5852AL),
            PlaylistData("qp4", "Odaklan",       0xFF2E9B8AL),
            PlaylistData("qp5", "Derin Mavi",    0xFF4E8B5FL),
            PlaylistData("qp6", "Yaz Anıları",   0xFF3B89A0L),
        )

        val RECENTLY_PLAYED = listOf(
            TrackData("rp1", "Neon Sokaklar", "Şehir Işıkları", 0xFFB5852AL),
            TrackData("rp2", "Derin Mavi",    "Okyanus",        0xFF4E8B5FL),
            TrackData("rp3", "Yıldız Tozu",   "Polaris",        0xFF2E9B8AL),
            TrackData("rp4", "Gece Yarısı",   "Eclipse",        0xFF7B5EA7L),
        )

        val RECOMMENDED_PLAYLISTS = listOf(
            PlaylistData("rc1", "Akşam Rüzgarı",  0xFF6B5B9CL),
            PlaylistData("rc2", "Haftalık Keşif", 0xFF4B5FA6L),
            PlaylistData("rc3", "Türkçe Top 50",  0xFF3A7D44L),
            PlaylistData("rc4", "Yaz Akşamları",  0xFFB5852AL),
        )
    }
}
