package ru.sber_tech.domain.getAddress

class GetAddressUseCase(private val repository: GetAddressRepo) {

    suspend operator fun invoke(latitude: Double, longitude: Double): String? {
        return repository.getAddressByGeo(latitude, longitude)
    }

}