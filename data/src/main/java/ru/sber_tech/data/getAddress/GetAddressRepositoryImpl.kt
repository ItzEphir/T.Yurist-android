package ru.sber_tech.data.getAddress

import ru.sber_tech.domain.getAddress.GetAddressRepository

class GetAddressRepositoryImpl(private val service: GetAddressService) : GetAddressRepository {
    override suspend fun getAddressByGeo(latitude: Double, longitude: Double): String? {
        return service.getAddress(latitude, longitude)
    }
}