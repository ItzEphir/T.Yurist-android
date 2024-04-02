package ru.sber_tech.data.getAddress

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class GetAddressService(private val client: HttpClient) {

    private val json = Json { ignoreUnknownKeys = true }
    suspend fun getAddress(latitude: Double, longitude: Double): String? {
        return try {
            val response =
                client.get("https://geocode-maps.yandex.ru/1.x/?apikey=e17f8f6a-8444-4283-98ad-7e49c56a6deb&geocode=$latitude,$longitude&format=json&lang=ru_RU&sco=latlong&kind=house&results=1") {}
            val jsonString: String = response.bodyAsText()

            println(jsonString)
            json.decodeFromString<GetAddressDto>(string = jsonString).response.GeoObjectCollection.featureMember[0]

                .GeoObject.metaDataProperty.GeocoderMetaData.Address.formatted
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}