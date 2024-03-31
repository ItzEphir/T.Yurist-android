package ru.sber_tech.data.addMeetScreen

import ru.sber_tech.domain.addMeetScreen.AddMeetModel
import ru.sber_tech.domain.addMeetScreen.AddMeetRepository
import ru.sber_tech.domain.addMeetScreen.MeetStatus

class AddMeetRepositoryImpl(
    private val dataSource: AddMeetDataSource
): AddMeetRepository {
    override suspend fun addMeet(model: AddMeetModel): MeetStatus {
        return dataSource.addMeet(model)
    }
}