package ru.sber_tech.data.operations

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json
import ru.sber_tech.domain.operations.OperationModel

class OperationsService(
    private val httpClient: HttpClient
) {

    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getOperations(): List<OperationModel>?{
        return try {
            val response = httpClient.get("http://158.160.114.41:8080/operations")
            json.decodeFromString<List<OperationsDto>>(response.bodyAsText()).map {
                OperationModel(
                    name = it.name,
                    documents = it.documents,
                    id = it.id,
                    duration = it.duration,
                    product = it.product
                )
            }
        } catch (e: Exception) {
            null
        }
    }
}