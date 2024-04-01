package ru.sber_tech.data.meets

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShortMeetDto(
    @SerialName("datetime")
    val dateTime: String,
    @SerialName("place_address")
    val address:String,
    @SerialName("place_latitude")
    val latitude: Double,
    @SerialName("place_longtitude")
    val longitude: Double,
    @SerialName("operations_ids")
    val operationsIds: List<Int>
)
