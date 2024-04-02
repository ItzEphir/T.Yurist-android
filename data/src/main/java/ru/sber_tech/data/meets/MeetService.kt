package ru.sber_tech.data.meets

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class MeetService(private val httpClient: HttpClient) {
    private val json = Json { ignoreUnknownKeys = true }
    suspend fun getMeets(limit: Int, offset: Int): List<MeetDto>? {
        return try {
            val response =
                httpClient.get("http://158.160.114.41:8080/meets"){
                    headers["Content-Type"]="application/json"
                }
            val jsonString = response.bodyAsText()
            json.decodeFromString<List<MeetDto>>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun getMeet(id: String): MeetDto? {
        return try {
            val response =
                httpClient.get("http://158.160.114.41:8080/meet/$id"){
                    headers["Content-Type"]="application/json"
                }
            val jsonString = response.bodyAsText()
            json.decodeFromString<MeetDto>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    suspend fun createMeet(meet: ShortMeetDto): MeetDto? {
        return try {
            val response = httpClient.post("http://158.160.114.41:8080/meet"){
                headers["Content-Type"]="application/json"
                setBody(meet)
            }
            val jsonString = response.bodyAsText()
            println(jsonString)
            json.decodeFromString<MeetDto>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun updateMeet(id: String, meet: ShortMeetDto): MeetDto? {
        return try {
            val response = httpClient.patch("http://158.160.114.41:8080/meet/$id") {
                headers["Content-Type"]="application/json"
                setBody(meet)
            }
            val jsonString = response.bodyAsText()
            json.decodeFromString<MeetDto>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun deleteMeet(id: String): Boolean {
        return try {
            val response = httpClient.delete("http://158.160.114.41:8080/meets/$id"){
                headers["Content-Type"]="application/json"
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}