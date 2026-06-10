package com.turkcell.lyraapp.data.repository

interface AuthRepository {
    suspend fun login(phone: String, password: String): Result<Unit>
}
