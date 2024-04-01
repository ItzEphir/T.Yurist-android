package ru.sber_tech.prod_mobile.screens.addMeetScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sber_tech.data.addMeetScreen.ErrorState
import ru.sber_tech.data.addMeetScreen.ErrorState.*
import ru.sber_tech.domain.addMeetScreen.AddMeetModel
import ru.sber_tech.domain.addMeetScreen.AddMeetState
import ru.sber_tech.domain.addMeetScreen.AddMeetState.Adding
import ru.sber_tech.domain.addMeetScreen.AddMeetState.Loading
import ru.sber_tech.domain.addMeetScreen.AddMeetUseCase
import ru.sber_tech.domain.addMeetScreen.MeetStatus.ERROR_ON_RECEIPT
import ru.sber_tech.domain.addMeetScreen.MeetStatus.SUCCESS

class AddMeetScreenViewModel(private val addMeetUseCase: AddMeetUseCase) : ViewModel() {
    
    private val _addMeetState = MutableStateFlow<AddMeetState>(Loading)
    val addMeetState = _addMeetState.asStateFlow()
    
    private val _errorState = MutableStateFlow<ErrorState?>(null)
    val errorState = _errorState.asStateFlow()
    
    fun loadElements() {
        _addMeetState.value = Adding(model = AddMeetModel(emptyList(), "", ""))
    }
    
    fun addOrDeleteElement(element: String) {
        _errorState.value = null
        if (addMeetState.value is Adding) {
            val addingState = addMeetState.value as Adding
            if (element in addingState.model.selectedEvents) {
                _addMeetState.value = addingState.copy(
                    model = addingState.model.copy(selectedEvents = addingState.model.selectedEvents.filter { it != element })
                )
            } else {
                _addMeetState.value = addingState.copy(
                    model = addingState.model.copy(
                        selectedEvents = addingState.model.selectedEvents + listOf(element)
                    )
                )
            }
        }
    }
    
    fun setDate(date: String) {
        _errorState.value = null
        if (addMeetState.value is Adding) {
            val addingState = addMeetState.value as Adding
            _addMeetState.value = addingState.copy(
                model = addingState.model.copy(
                    date = date
                )
            )
        }
    }
    
    fun setTime(time: String) {
        _errorState.value = null
        if (addMeetState.value is Adding) {
            val addingState = addMeetState.value as Adding
            _addMeetState.value = addingState.copy(
                model = addingState.model.copy(
                    time = time
                )
            )
        }
    }
    
    fun publish(onSuccess: () -> Unit) {
        if (addMeetState.value is Adding) {
            val addingState = addMeetState.value as Adding
            if (addingState.model.date == "") _errorState.value = DateError
            if (addingState.model.time == "") _errorState.value = TimeError
            if (addingState.model.selectedEvents.isEmpty()) _errorState.value = EventError
            
            if(errorState.value != null){
                return
            }
            
            viewModelScope.launch {
                when (addMeetUseCase.execute(addingState.model)) {
                    SUCCESS          -> onSuccess()
                    ERROR_ON_RECEIPT -> _errorState.value = ReceiptError
                }
            }
        }
    }
}