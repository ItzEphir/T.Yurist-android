package ru.sber_tech.domain.mainScreen

class GetAllMeetsUseCase(
    private val repository: AllMeetsRepository
) {

    suspend fun execute(): AllMeetsState {
        return when(val meets = repository.getAllMeets(1)){
            null -> AllMeetsState.ErrorOnReceipt
            else -> AllMeetsState.AllMeets(meets)
        }
    }
}