package ru.sber_tech.domain.mainScreen

class GetAllMeetsUseCase(
    private val repository: AllMeetsRepository
) {

    suspend fun execute(): AllMeetsState {
        return repository.getAllMeets()
    }
}