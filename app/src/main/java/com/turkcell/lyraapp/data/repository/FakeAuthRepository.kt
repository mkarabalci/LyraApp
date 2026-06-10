package com.turkcell.lyraapp.data.repository

import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeAuthRepository @Inject constructor() : AuthRepository {
    override suspend fun login(phone: String, password: String): Result<Unit> {
        delay(1000)
        return if (password.isNotBlank()) {
            Result.success(Unit)
        } else {
            Result.failure(IllegalArgumentException("Şifre boş olamaz."))
        }
    }

    override suspend fun register(
        firstName: String,
        lastName: String,
        phone: String,
        password: String,
    ): Result<Unit> {
        delay(1500)
        return Result.success(Unit)
    }
}
