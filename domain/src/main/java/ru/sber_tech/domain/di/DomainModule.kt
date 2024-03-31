package ru.sber_tech.domain.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.sber_tech.domain.addMeetScreen.AddMeetUseCase
import ru.sber_tech.domain.mainScreen.GetAllMeetsUseCase

val domainModule = module {
    factoryOf(::GetAllMeetsUseCase){bind<GetAllMeetsUseCase>()}
    factoryOf(::AddMeetUseCase){bind<AddMeetUseCase>()}
}