package ru.sber_tech.domain.di

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import ru.sber_tech.domain.addMeetScreen.AddMeetUseCase
import ru.sber_tech.domain.editMeetScreen.DeleteMeetUseCase
import ru.sber_tech.domain.editMeetScreen.EditMeetUseCase
import ru.sber_tech.domain.editMeetScreen.GetMeetUseCase
import ru.sber_tech.domain.getAddress.GetAddressUseCase
import ru.sber_tech.domain.getCoordinates.GetCoordinatesByAddressRepository
import ru.sber_tech.domain.getCoordinates.GetCoordinatesByAddressUseCase
import ru.sber_tech.domain.mainScreen.GetAllMeetsUseCase
import ru.sber_tech.domain.operations.GetOperationsUseCase
import ru.sber_tech.domain.searchScreen.SearchUseCase

val domainModule = module {
    factoryOf(::GetAllMeetsUseCase)
    factoryOf(::AddMeetUseCase)
    factoryOf(::GetAddressUseCase)
    factoryOf(::GetCoordinatesByAddressUseCase)
    factoryOf(::SearchUseCase)
    factoryOf(::GetOperationsUseCase)
    factoryOf(::EditMeetUseCase)
    factoryOf(::GetMeetUseCase)
    factoryOf(::DeleteMeetUseCase)
}