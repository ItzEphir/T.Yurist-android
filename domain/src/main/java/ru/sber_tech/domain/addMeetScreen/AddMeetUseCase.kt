package ru.sber_tech.domain.addMeetScreen

class AddMeetUseCase(
    private val repository: AddMeetRepository
) {
    suspend fun execute(model: AddMeetModel): MeetStatus{
        return repository.addMeet(model)
    }
}