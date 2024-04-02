package ru.sber_tech.domain.editMeetScreen

import ru.sber_tech.domain.mainScreen.LiteMeetModel

interface EditMeetRepository {
    suspend fun updateMeet(id: String, meetModel: EditMeetModel): MeetModel?

    suspend fun getMeet(id: String): EditMeetModel?
    
    suspend fun deleteMeet(id: String): Boolean
}