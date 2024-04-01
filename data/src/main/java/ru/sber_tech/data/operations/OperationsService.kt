package ru.sber_tech.data.operations

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ru.sber_tech.domain.operations.OperationModel

class OperationsService(
    private val httpClient: HttpClient,
) {
    
    private val json = Json { ignoreUnknownKeys = true }
    
    suspend fun getOperations(): List<OperationsDto>? {
        return try {
            val response = httpClient.get("http://158.160.114.41:8080/operations")
            json.decodeFromString<List<OperationsDto>>(response.bodyAsText())
        } catch (e: Exception) {
            null
        }
    }
    
    suspend fun getOperationById(id: Int): OperationsDto? {
        return try {
            httpClient.get("http://158.160.114.41:8080/operation/$id").body<OperationsDto>()
        } catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}