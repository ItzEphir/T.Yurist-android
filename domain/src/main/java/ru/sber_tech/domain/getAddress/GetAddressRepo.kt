package ru.sber_tech.domain.getAddress

interface GetAddressRepo {
    suspend fun getAddressByGeo(latitude: Double, longitude: Double): String?
}