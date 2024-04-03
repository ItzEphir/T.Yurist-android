package ru.sber_tech.data.editMeetScreen

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class RepresentativeService(private val httpClient: HttpClient) {
    suspend fun getRepresentative(id: Int): RepresentativeDto? {
        return try {
            httpClient.get("http://158.160.114.41:8080/representatives/$id")
                .body<RepresentativeDto>()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}