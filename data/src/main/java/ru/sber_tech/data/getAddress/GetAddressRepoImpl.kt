package ru.sber_tech.data.getAddress

import ru.sber_tech.domain.getAddress.GetAddressRepo

class GetAddressRepoImpl(private val service: GetAddressService) : GetAddressRepo {
    override suspend fun getAddressByGeo(latitude: Double, longitude: Double): String? {
        return service.getAddress(latitude, longitude)
    }
}