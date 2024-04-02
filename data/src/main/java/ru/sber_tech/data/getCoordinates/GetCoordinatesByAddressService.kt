package ru.sber_tech.data.getCoordinates

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ru.sber_tech.domain.getCoordinates.CoordinatesPoint

class GetCoordinatesByAddressService(private val httpClient: HttpClient) {
    private val json = Json { ignoreUnknownKeys = true }
    suspend fun getCoordinates(address: String): CoordinatesPoint? {
        return try {
            println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHh")
            val response =
                httpClient.get("https://geocode-maps.yandex.ru/1.x/?apikey=e17f8f6a-8444-4283-98ad-7e49c56a6deb&geocode=$address&format=json&lang=ru_RU&results=1")
            val jsonString: String = response.bodyAsText()
            println(jsonString)
            json.decodeFromString<GetCoordsDto>(jsonString).response.GeoObjectCollection.featureMember[0].GeoObject.Point.convert()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}