package ru.sber_tech.domain.mainScreen

interface AllMeetsRepository {
    suspend fun getAllMeets(page: Int): List<LiteMeetModel>?
}