package ru.sber_tech.data.searchScreen

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ru.sber_tech.data.getCoordinates.GetCoordsDto
import ru.sber_tech.data.getCoordinates.convert
import ru.sber_tech.domain.getCoordinates.CoordinatesPoint

class SearchService(private val httpClient: HttpClient) {
    private val json = Json { ignoreUnknownKeys = true }
    suspend fun getAddresses(text: String, coordinatesPoint: CoordinatesPoint?): List<String> {
        return try {
            val response =
                if (coordinatesPoint != null) {

                    httpClient.get("https://suggest-maps.yandex.ru/v1/suggest?apikey=9e4a8c1d-5a94-4c0b-b370-830c01870b89&text=$text&types=house&ll=${coordinatesPoint.latitude},${coordinatesPoint.longitude}")
                } else {
                    httpClient.get("https://suggest-maps.yandex.ru/v1/suggest?apikey=9e4a8c1d-5a94-4c0b-b370-830c01870b89&text=$text&types=house")
                }
            val jsonString: String = response.bodyAsText()
            println(jsonString)
            json.decodeFromString<AddressDto>(jsonString).results.map { it.title.text }
        } catch (e: Exception) {
            emptyList()
        }
    }
}