package ru.sber_tech.domain.addMeetScreen

import ru.sber_tech.domain.operations.OperationModel

data class AddMeetModel(
    val selectedEvents: List<OperationModel>,
    val time: String,
    val date: String,
    val latitude: Double,
    val longitude: Double,
    val address: String,
)