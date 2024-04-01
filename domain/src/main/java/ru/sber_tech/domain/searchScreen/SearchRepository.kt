package ru.sber_tech.domain.searchScreen

import ru.sber_tech.domain.getCoordinates.CoordinatesPoint

interface SearchRepository {
    suspend fun getAddresses(text: String, coordinatesPoint: CoordinatesPoint?): List<String>
}