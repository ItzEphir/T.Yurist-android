package ru.sber_tech.data.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.sber_tech.data.addMeetScreen.AddMeetDataSource
import ru.sber_tech.data.addMeetScreen.AddMeetRepositoryImpl
import ru.sber_tech.data.getAllMeets.AllMeetsDataSource
import ru.sber_tech.data.getAllMeets.AllMeetsRepositoryImpl
import ru.sber_tech.domain.addMeetScreen.AddMeetRepository
import ru.sber_tech.domain.mainScreen.AllMeetsRepository

val dataModule = module {
    singleOf(::AllMeetsDataSource){bind<AllMeetsDataSource>()}
    singleOf(::AllMeetsRepositoryImpl){bind<AllMeetsRepository>()}
    singleOf(::AddMeetRepositoryImpl){bind<AddMeetRepository>()}
    singleOf(::AddMeetDataSource){bind<AddMeetDataSource>()}
}