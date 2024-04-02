package ru.sber_tech.domain.editMeetScreen

class EditMeetUseCase(private val editMeetRepository: EditMeetRepository) {
    suspend operator fun invoke(id: String, meetModel: EditMeetModel) = editMeetRepository.updateMeet(id, meetModel) != null
}