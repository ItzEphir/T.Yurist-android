package ru.sber_tech.data.getCoordinates

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ru.sber_tech.domain.getCoordinates.CoordinatesPoint

class GetCoordinatesByAddressService(val httpClient: HttpClient) {
    private val json = Json { ignoreUnknownKeys = true }
    suspend fun getCoordinates(address: String): CoordinatesPoint? {
        return try {
            val response =
                httpClient.get("https://geocode-maps.yandex.ru/1.x/?apikey=947c501d-64a3-4725-87a5-3c6995e9c104&geocode=$address&format=json&lang=ru_RU&results=1")
            val jsonString: String = response.bodyAsText()
            println(jsonString)
            json.decodeFromString<GetCoordsDto>(jsonString).response.GeoObjectCollection.featureMember[0].GeoObject.Point.convert()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}