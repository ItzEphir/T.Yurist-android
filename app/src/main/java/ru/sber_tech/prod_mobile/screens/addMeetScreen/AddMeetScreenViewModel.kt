package ru.sber_tech.prod_mobile.screens.addMeetScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AddMeetScreenViewModel: ViewModel() {

    private val _segmentedButtonState = MutableStateFlow<List<String>>(emptyList())
    val segmentedButtonState = _segmentedButtonState.asStateFlow()

    fun addOrDeleteElement(element: String){
        if (element in segmentedButtonState.value){
            _segmentedButtonState.value = _segmentedButtonState.value.filter { it != element }
        }
        else{
            _segmentedButtonState.value += element
        }
    }
}