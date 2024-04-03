package ru.sber_tech.data.editMeetScreen

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepresentativeDto(
    val id: Int,
    val name: String,
    @SerialName("photo_inner_url")
    val photoInnerUrl: String,
    val position: String,
    val surname: String
)