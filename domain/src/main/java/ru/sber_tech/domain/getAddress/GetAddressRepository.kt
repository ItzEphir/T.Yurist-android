package ru.sber_tech.domain.getAddress

interface GetAddressRepository {
    suspend fun getAddressByGeo(latitude: Double, longitude: Double): String?
}