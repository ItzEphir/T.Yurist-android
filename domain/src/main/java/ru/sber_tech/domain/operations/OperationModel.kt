package ru.sber_tech.domain.operations

data class OperationModel(
    val name: String,
    val documents: List<String>,
    val id: Int,
    val duration: String,
    val product: String
)