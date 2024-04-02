package ru.sber_tech.domain.editMeetScreen

sealed interface EditMeetState {
    data object Loading : EditMeetState
    
    data class Editing(val model: EditMeetModel): EditMeetState
    
    data object ErrorOnReceipt: EditMeetState
}