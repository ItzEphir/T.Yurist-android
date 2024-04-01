package ru.sber_tech.domain.mainScreen

import ru.sber_tech.domain.operations.OperationModel

data class LiteMeetModel(
    val id: String,
    val operations: List<OperationModel>,
    val address: String,
    val date: String,
    val time: String,
    val endTime: String,
    val peoples: List<String>
)
