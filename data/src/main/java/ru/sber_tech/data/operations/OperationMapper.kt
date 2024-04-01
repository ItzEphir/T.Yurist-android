package ru.sber_tech.data.operations

import ru.sber_tech.domain.operations.OperationModel

fun OperationsDto.toDomain(): OperationModel = OperationModel(
    name = name,
    documents = documents,
    duration = duration,
    id = id,
    product = product,
)