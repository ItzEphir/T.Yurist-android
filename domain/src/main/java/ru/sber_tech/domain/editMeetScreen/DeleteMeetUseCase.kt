package ru.sber_tech.domain.editMeetScreen

class DeleteMeetUseCase(private val repository: EditMeetRepository) {
    suspend operator fun invoke(id: String) = repository.deleteMeet(id)
}