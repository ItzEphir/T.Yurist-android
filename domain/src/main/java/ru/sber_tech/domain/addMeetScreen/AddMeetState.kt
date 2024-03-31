package ru.sber_tech.domain.addMeetScreen

sealed interface AddMeetState {
    data object Loading : AddMeetState
    
    data class Adding(val model: AddMeetModel
    ) : AddMeetState
    
    data object ErrorOnReceipt : AddMeetState
}