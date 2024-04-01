package ru.sber_tech.domain.getCoordinates

interface GetCoordinatesByAddressRepository {
    suspend fun getCoordinatesByAddress(address: String): CoordinatesPoint?
}