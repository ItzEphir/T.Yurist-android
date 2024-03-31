package ru.sber_tech.data.getAllMeets

import kotlinx.coroutines.delay
import ru.sber_tech.domain.mainScreen.AllMeetsState
import ru.sber_tech.domain.mainScreen.LiteMeetModel

class AllMeetsDataSource {
    suspend fun getAllMeets(): AllMeetsState {
        delay(2000)
        return AllMeetsState.AllMeets(listOf(LiteMeetModel("sdsd"), LiteMeetModel("sdsdsdsd")))
    }
}