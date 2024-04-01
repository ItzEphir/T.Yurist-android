package ru.sber_tech.data.operations

import kotlinx.serialization.Serializable

@Serializable
data class OperationsDto(
    val name: String,
    val product: String,
    val documents: List<String>,
    val duration: String,
    val id: Int
)