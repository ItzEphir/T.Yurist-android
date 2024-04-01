package ru.sber_tech.domain.searchScreen

import ru.sber_tech.domain.getCoordinates.CoordinatesPoint
import ru.sber_tech.domain.searchScreen.SearchRepository

class SearchUseCase(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(text: String, coordinatesPoint: CoordinatesPoint?) = searchRepository.getAddresses(text, coordinatesPoint)
}