package ru.sber_tech.domain.editMeetScreen

import ru.sber_tech.domain.operations.OperationModel

data class EditMeetModel(
    val selectedEvents: List<OperationModel>,
    val time: String,
    val date: String,
    val latitude: Double,
    val longitude: Double,
    val representative: RepresentativeModel?,
    val address: String,
    val people: List<PersonModel>,
)
