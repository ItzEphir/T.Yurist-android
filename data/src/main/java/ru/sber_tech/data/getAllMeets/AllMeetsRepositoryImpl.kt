package ru.sber_tech.data.getAllMeets

import ru.sber_tech.domain.mainScreen.AllMeetsRepository
import ru.sber_tech.domain.mainScreen.AllMeetsState

class AllMeetsRepositoryImpl(private val dataSource: AllMeetsDataSource) : AllMeetsRepository{
    override suspend fun getAllMeets(): AllMeetsState {
        return dataSource.getAllMeets()
    }
}