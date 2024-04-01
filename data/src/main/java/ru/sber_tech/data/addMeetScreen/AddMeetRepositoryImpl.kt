package ru.sber_tech.data.addMeetScreen

import ru.sber_tech.data.meets.MeetService
import ru.sber_tech.domain.addMeetScreen.AddMeetModel
import ru.sber_tech.domain.addMeetScreen.AddMeetRepository
import ru.sber_tech.domain.addMeetScreen.MeetStatus
import ru.sber_tech.domain.addMeetScreen.MeetStatus.ERROR_ON_RECEIPT
import ru.sber_tech.domain.addMeetScreen.MeetStatus.SUCCESS

class AddMeetRepositoryImpl(
    private val service: MeetService
): AddMeetRepository {
    override suspend fun addMeet(model: AddMeetModel): MeetStatus {
        return when(service.createMeet(model.toDto())){
            null -> ERROR_ON_RECEIPT
            else -> SUCCESS
        }
    }
}