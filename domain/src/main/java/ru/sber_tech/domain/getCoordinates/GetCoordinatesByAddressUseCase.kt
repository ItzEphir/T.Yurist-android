package ru.sber_tech.domain.getCoordinates

class GetCoordinatesByAddressUseCase(private val getCoordinatesByAddressRepository: GetCoordinatesByAddressRepository) {
    suspend operator fun invoke(address: String) =
        getCoordinatesByAddressRepository.getCoordinatesByAddress(address)
}