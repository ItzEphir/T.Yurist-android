package ru.sber_tech.domain.addMeetScreen

data class AddMeetModel(
    val selectedEvents: List<String>,
    val time: String,
    val date: String,
)