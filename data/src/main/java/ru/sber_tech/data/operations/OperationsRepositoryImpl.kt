package ru.sber_tech.data.operations

import ru.sber_tech.domain.operations.OperationModel
import ru.sber_tech.domain.operations.OperationsRepository

class OperationsRepositoryImpl(private val service: OperationsService) : OperationsRepository {
    override suspend fun getOperations(): List<OperationModel>? {
        return service.getOperations()?.map {
            it.toDomain()
        }
    }
}