package com.turkcell.lyraapp.data.auth

interface AuthRepository {
    suspend fun login(phone: String, password: String): Result<Unit>
    suspend fun register(
        firstName: String,
        lastName: String,
        phone: String,
        password: String,
    ): Result<Unit>
}
