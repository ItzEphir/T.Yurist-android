package ru.sber_tech.data.getCoordinates

import ru.sber_tech.domain.getCoordinates.CoordinatesPoint
import ru.sber_tech.domain.getCoordinates.GetCoordinatesByAddressRepository

class GetCoordinatesByAddressRepositoryImpl(private val getCoordinatesByAddressService: GetCoordinatesByAddressService) :
    GetCoordinatesByAddressRepository {
    override suspend fun getCoordinatesByAddress(address: String): CoordinatesPoint? {
        return getCoordinatesByAddressService.getCoordinates(address)
    }
}