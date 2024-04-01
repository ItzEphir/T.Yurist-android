package ru.sber_tech.prod_mobile.screens.editMeetScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sber_tech.domain.editMeetState.EditMeetModel
import ru.sber_tech.domain.editMeetState.EditMeetState
import ru.sber_tech.domain.editMeetState.EditMeetState.Editing
import ru.sber_tech.domain.editMeetState.EditMeetState.Loading

class EditMeetScreenViewModel : ViewModel() {
    private val _editMeetState = MutableStateFlow<EditMeetState>(Loading)
    val editMeetState = _editMeetState.asStateFlow()
    
    fun load() {
        viewModelScope.launch {
            _editMeetState.value = Editing(EditMeetModel(emptyList(), "", ""))
        }
    }
    
    fun addOrDeleteElement(element: String) {
        if (editMeetState.value is Editing) {
            val editingState = editMeetState.value as Editing
            if (element in editingState.model.selectedEvents) {
                _editMeetState.value = editingState.copy(
                    model = editingState.model.copy(selectedEvents = editingState.model.selectedEvents.filter { it != element })
                )
            } else {
                _editMeetState.value = editingState.copy(
                    model = editingState.model.copy(
                        selectedEvents = editingState.model.selectedEvents + listOf(element)
                    )
                )
            }
        }
    }
}