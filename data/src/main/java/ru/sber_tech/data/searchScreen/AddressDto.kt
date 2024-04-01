package ru.sber_tech.data.searchScreen

import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    val results: List<ResultDto>
)

@Serializable
data class ResultDto(
    val title : TitleDto
)

@Serializable
data class TitleDto(
    val text: String
)