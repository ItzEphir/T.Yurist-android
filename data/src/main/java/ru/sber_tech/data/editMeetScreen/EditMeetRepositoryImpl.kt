package ru.sber_tech.data.editMeetScreen

import ru.sber_tech.data.addMeetScreen.toDomain
import ru.sber_tech.data.addMeetScreen.toEditMeetModel
import ru.sber_tech.data.addMeetScreen.toMeetModel
import ru.sber_tech.data.addMeetScreen.toShortMeet
import ru.sber_tech.data.meets.MeetService
import ru.sber_tech.data.operations.OperationsService
import ru.sber_tech.data.operations.toDomain
import ru.sber_tech.domain.editMeetScreen.EditMeetModel
import ru.sber_tech.domain.editMeetScreen.EditMeetRepository
import ru.sber_tech.domain.editMeetScreen.MeetModel

class EditMeetRepositoryImpl(
    private val service: MeetService,
    private val operationsService: OperationsService,
    private val representativeService: RepresentativeService
) : EditMeetRepository {
    override suspend fun updateMeet(id: String, meetModel: EditMeetModel): MeetModel? {
        val response = service.updateMeet(id, meetModel.toShortMeet())
        val operations = response?.operationIds?.mapNotNull {
            operationsService.getOperationById(it)?.toDomain()
        }
        return response?.toMeetModel(operations ?: emptyList())
    }
    
    override suspend fun getMeet(id: String): EditMeetModel? {
        val response = service.getMeet(id)
        val operations = response?.operationIds?.mapNotNull {
            operationsService.getOperationById(it)?.toDomain()
        }
        val representative = response?.representativeId?.let {
            representativeService.getRepresentative(
                it
            )
        }
        
        return response?.toEditMeetModel(operations ?: emptyList(), representative?.toDomain())
    }
    
    override suspend fun deleteMeet(id: String): Boolean {
        return service.deleteMeet(id)
    }
}