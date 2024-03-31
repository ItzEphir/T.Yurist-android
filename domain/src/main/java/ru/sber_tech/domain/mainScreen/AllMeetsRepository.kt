package ru.sber_tech.domain.mainScreen

interface AllMeetsRepository {
    suspend fun getAllMeets(): AllMeetsState
}