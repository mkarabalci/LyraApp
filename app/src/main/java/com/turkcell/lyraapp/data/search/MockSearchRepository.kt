package com.turkcell.lyraapp.data.search

import javax.inject.Inject

class MockSearchRepository @Inject constructor() : SearchRepository {

    override suspend fun getGenres(): Result<List<Genre>> =
        Result.success(GENRES)

    private companion object {
        val GENRES = listOf(
            Genre("pop",        "Pop",        0xFF4DB6ACL),
            Genre("elektronik", "Elektronik", 0xFF7C4DFFL),
            Genre("akustik",    "Akustik",    0xFF9575CDL),
            Genre("lofi",       "Lo-fi",      0xFF26897EL),
            Genre("indie",      "Indie",      0xFF3949ABL),
            Genre("jazz",       "Jazz",       0xFF558B2FL),
            Genre("klasik",     "Klasik",     0xFFAD7E8FL),
            Genre("yolculuk",   "Yolculuk",   0xFFD4856AL),
        )
    }
}
