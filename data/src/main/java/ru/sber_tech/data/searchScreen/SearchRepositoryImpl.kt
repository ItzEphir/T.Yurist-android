package ru.sber_tech.data.searchScreen

import ru.sber_tech.domain.getCoordinates.CoordinatesPoint
import ru.sber_tech.domain.searchScreen.SearchRepository

class SearchRepositoryImpl(private val searchService: SearchService): SearchRepository {
    override suspend fun getAddresses(
        text: String,
        coordinatesPoint: CoordinatesPoint?
    ): List<String> = searchService.getAddresses(text,coordinatesPoint)
}