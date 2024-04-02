package ru.sber_tech.prod_mobile.screens.editMeetScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sber_tech.domain.editMeetScreen.DeleteMeetUseCase
import ru.sber_tech.domain.editMeetScreen.EditMeetState
import ru.sber_tech.domain.editMeetScreen.EditMeetState.*
import ru.sber_tech.domain.editMeetScreen.EditMeetUseCase
import ru.sber_tech.domain.editMeetScreen.GetMeetUseCase
import ru.sber_tech.domain.operations.GetOperationsUseCase
import ru.sber_tech.domain.operations.OperationModel

class EditMeetScreenViewModel(
    private val editMeetUseCase: EditMeetUseCase,
    private val getMeetUseCase: GetMeetUseCase,
    private val getOperationsUseCase: GetOperationsUseCase,
    private val deleteMeetUseCase: DeleteMeetUseCase,
) : ViewModel() {
    private val _editMeetState = MutableStateFlow<EditMeetState>(Loading)
    val editMeetState = _editMeetState.asStateFlow()
    
    private val _operations = MutableStateFlow<List<OperationModel>>(emptyList())
    val operations = _operations.asStateFlow()
    
    fun load(id: String) {
        viewModelScope.launch {
            when (val operations = getOperationsUseCase.invoke()) {
                null -> _editMeetState.value = ErrorOnReceipt
                else -> _operations.value = operations
            }
            when (val editMeetModel = getMeetUseCase.invoke(id)) {
                null -> _editMeetState.value = ErrorOnReceipt
                else -> _editMeetState.value = Editing(model = editMeetModel)
            }
        }
    }
    
    fun reload(id: String){
        _editMeetState.value = Loading
        load(id)
    }
    
    fun addOrDeleteElement(element: OperationModel) {
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
    
    fun setDate(date: String) {
        if (editMeetState.value is Editing) {
            val editingState = editMeetState.value as Editing
            _editMeetState.value = editingState.copy(
                model = editingState.model.copy(
                    date = date
                )
            )
        }
    }
    
    fun setTime(time: String) {
        if (editMeetState.value is Editing) {
            val editingState = editMeetState.value as Editing
            _editMeetState.value = editingState.copy(
                model = editingState.model.copy(
                    time = time
                )
            )
        }
    }
    
    fun publish(id: String, onSuccess: () -> Unit, onError: () -> Unit) {
        if (editMeetState.value is Editing) {
            val editingState = editMeetState.value as Editing
            
            viewModelScope.launch {
                when (editMeetUseCase.invoke(id, editingState.model)) {
                    true  -> onSuccess()
                    false -> onError()
                }
            }
        }
    }
    
    fun delete(id: String, onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            if (deleteMeetUseCase(id)) {
                onSuccess()
            } else {
                onError()
            }
        }
    }
}