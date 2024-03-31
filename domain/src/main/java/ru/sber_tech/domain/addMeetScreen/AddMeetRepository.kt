package ru.sber_tech.domain.addMeetScreen

interface AddMeetRepository {
    suspend fun addMeet(model: AddMeetModel): MeetStatus
}