package ru.sber_tech.domain.getAddress

class GetAddressUseCase(private val repository: GetAddressRepository) {

    suspend operator fun invoke(latitude: Double, longitude: Double): String? {
        return repository.getAddressByGeo(latitude, longitude)
    }

}