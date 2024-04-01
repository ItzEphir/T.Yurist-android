package ru.sber_tech.domain.operations

interface OperationsRepository {
    suspend fun getOperations(): List<OperationModel>?
}