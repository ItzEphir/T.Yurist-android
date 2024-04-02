package ru.sber_tech.data.getAllMeets

import ru.sber_tech.data.addMeetScreen.toLiteModel
import ru.sber_tech.data.meets.MeetService
import ru.sber_tech.data.operations.OperationsService
import ru.sber_tech.data.operations.toDomain
import ru.sber_tech.domain.mainScreen.AllMeetsRepository
import ru.sber_tech.domain.mainScreen.AllMeetsState
import ru.sber_tech.domain.mainScreen.LiteMeetModel

class AllMeetsRepositoryImpl(
    private val service: MeetService,
    private val operationsService: OperationsService
) : AllMeetsRepository {
    override suspend fun getAllMeets(page: Int): List<LiteMeetModel>? {
        return service.getMeets(limit = 100, offset = (page - 1) * 10)?.map { meetDto ->
            val operations = meetDto.operationIds.mapNotNull {
                operationsService.getOperationById(it)?.toDomain()
            }
            meetDto.toLiteModel(operations)
        }
    }
}