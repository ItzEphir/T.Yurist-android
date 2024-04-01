package ru.sber_tech.data.addMeetScreen

import kotlinx.coroutines.delay
import ru.sber_tech.domain.addMeetScreen.AddMeetModel
import ru.sber_tech.domain.addMeetScreen.MeetStatus

class AddMeetDataSource {

    suspend fun addMeet(model: AddMeetModel): MeetStatus{
        delay(3000)
        return MeetStatus.ERROR_ON_RECEIPT
    }
}