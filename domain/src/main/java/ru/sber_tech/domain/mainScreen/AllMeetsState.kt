package ru.sber_tech.domain.mainScreen

sealed interface AllMeetsState {
    data object Loading: AllMeetsState
    data class AllMeets(val list: List<LiteMeetModel>): AllMeetsState
    data object ErrorOnReceipt: AllMeetsState
}