package ru.sber_tech.domain.operations

class GetOperationsUseCase(private val operationsRepository: OperationsRepository) {
    suspend operator fun invoke() = operationsRepository.getOperations()
}