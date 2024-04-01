package ru.sber_tech.data.meets

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MeetDto(
    @SerialName("datetime")
    val dateTime: String,
    @SerialName("place_address")
    val placeAddress: String,
    @SerialName("place_longtitude")
    val placeLongitude: Double,
    @SerialName("place_latitude")
    val placeLatitude: Double,
    @SerialName("operations_ids")
    val operationIds: List<Int>,
    val id: String,
    @SerialName("representative_id")
    val representativeId: Int? = null,
    @SerialName("approximate_end_datetime")
    val approximateEndDatetime: String,
    @SerialName("client_side_people")
    val clientSidePeople: List<Person>,
)

@Serializable
data class Person(
    val name: String,
    val surname: String,
    val position: String,
)
