package ru.sber_tech.domain.editMeetScreen

class GetMeetUseCase(private val editMeetRepository: EditMeetRepository) {
    
    suspend operator fun invoke(id: String) = editMeetRepository.getMeet(id)
    
}