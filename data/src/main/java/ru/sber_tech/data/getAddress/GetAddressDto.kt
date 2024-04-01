package ru.sber_tech.data.getAddress

import kotlinx.serialization.Serializable

@Serializable
data class GetAddressDto(val response: ResponseDto)


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
    val metaDataProperty: MetaDataPropertyDto
)

@Serializable
data class MetaDataPropertyDto(
    val GeocoderMetaData: GeocoderMetaDataDto
)

@Serializable
data class GeocoderMetaDataDto(
    val Address: Address
)


@Serializable
data class Address(
    val formatted: String
)
