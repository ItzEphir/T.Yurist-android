package ru.sber_tech.data.getCoordinates

import kotlinx.serialization.Serializable

@Serializable
data class GetCoordsDto(val response: ResponseDto)


@Serializable
data class ResponseDto(
    val GeoObjectCollection: GeoObjectCollectionDto
)

@Serializable
data class GeoObjectCollectionDto(
    val featureMember: List<FeatureMemberDto>
)

@Serializable
data class FeatureMemberDto(val GeoObject: GeoObjDto)

@Serializable
data class GeoObjDto(
    val Point: Point
)

@Serializable
data class Point(val pos: String)
