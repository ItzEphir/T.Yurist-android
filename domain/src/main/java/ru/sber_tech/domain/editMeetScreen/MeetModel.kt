package ru.sber_tech.domain.editMeetScreen

import ru.sber_tech.domain.operations.OperationModel

data class MeetModel(
    val id: String,
    val date: String,
    val time: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val operations: List<OperationModel>,
    val endDate: String,
    val endTime: String,
    val clients: List<PersonModel>
)
