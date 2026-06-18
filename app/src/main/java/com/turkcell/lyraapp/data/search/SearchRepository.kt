package com.turkcell.lyraapp.data.search

interface SearchRepository {
    suspend fun getGenres(): Result<List<Genre>>
}
