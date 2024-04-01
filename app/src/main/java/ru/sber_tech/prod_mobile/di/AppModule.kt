package ru.sber_tech.prod_mobile.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.sber_tech.prod_mobile.screens.addMeetScreen.AddMeetScreenViewModel
import ru.sber_tech.prod_mobile.screens.editMeetScreen.EditMeetScreenViewModel
import ru.sber_tech.prod_mobile.screens.mainScreen.MainScreenViewModel

val appModule = module {
    viewModelOf(::MainScreenViewModel)
    viewModelOf(::AddMeetScreenViewModel)
    viewModelOf(::EditMeetScreenViewModel)
}