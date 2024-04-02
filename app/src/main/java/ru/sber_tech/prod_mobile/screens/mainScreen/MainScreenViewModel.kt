package ru.sber_tech.prod_mobile.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sber_tech.domain.mainScreen.AllMeetsState
import ru.sber_tech.domain.mainScreen.AllMeetsState.Loading
import ru.sber_tech.domain.mainScreen.GetAllMeetsUseCase

class MainScreenViewModel(
    private val getAllMeetsUseCase: GetAllMeetsUseCase
): ViewModel() {
    private val _allMeetsState = MutableStateFlow<AllMeetsState>(Loading)
    val allMeetsState = _allMeetsState.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _allMeetsState.value = getAllMeetsUseCase.execute()
        }
    }
    
    fun refresh(onEnd: () -> Unit){
        viewModelScope.launch {
            _allMeetsState.value = getAllMeetsUseCase.execute()
            onEnd()
        }
        
    }
    
    fun reload() {
        _allMeetsState.value = Loading
        load()
    }
}