package ru.sber_tech.data.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sber_tech.data.addMeetScreen.AddMeetDataSource
import ru.sber_tech.data.addMeetScreen.AddMeetRepositoryImpl
import ru.sber_tech.data.getAddress.GetAddressRepositoryImpl
import ru.sber_tech.data.getAddress.GetAddressService
import ru.sber_tech.data.getAllMeets.AllMeetsDataSource
import ru.sber_tech.data.getAllMeets.AllMeetsRepositoryImpl
import ru.sber_tech.data.getCoordinates.GetCoordinatesByAddressRepositoryImpl
import ru.sber_tech.data.getCoordinates.GetCoordinatesByAddressService
import ru.sber_tech.data.searchScreen.SearchRepositoryImpl
import ru.sber_tech.data.searchScreen.SearchService
import ru.sber_tech.domain.addMeetScreen.AddMeetRepository
import ru.sber_tech.domain.getAddress.GetAddressRepository
import ru.sber_tech.domain.getCoordinates.GetCoordinatesByAddressRepository
import ru.sber_tech.domain.mainScreen.AllMeetsRepository
import ru.sber_tech.domain.searchScreen.SearchRepository

val dataModule = module {
    singleOf(::AllMeetsRepositoryImpl) { bind<AllMeetsRepository>() }
    singleOf(::AddMeetRepositoryImpl) { bind<AddMeetRepository>() }
    singleOf(::GetAddressRepositoryImpl) { bind<GetAddressRepository>() }
    singleOf(::GetCoordinatesByAddressRepositoryImpl) { bind<GetCoordinatesByAddressRepository>() }
    singleOf(::SearchRepositoryImpl) { bind<SearchRepository>() }
    singleOf(::AddMeetDataSource) { bind<AddMeetDataSource>() }
    singleOf(::AllMeetsDataSource) { bind<AllMeetsDataSource>() }
    singleOf(::GetAddressService) { bind<GetAddressService>() }
    singleOf(::GetCoordinatesByAddressService) { bind<GetCoordinatesByAddressService>() }
    singleOf(::SearchService) { bind<SearchService>() }

    single {
        HttpClient(OkHttp)
    } bind HttpClient::class
}