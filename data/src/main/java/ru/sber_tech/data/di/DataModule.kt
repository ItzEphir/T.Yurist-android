package ru.sber_tech.data.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import ru.sber_tech.data.addMeetScreen.AddMeetDataSource
import ru.sber_tech.data.addMeetScreen.AddMeetRepositoryImpl
import ru.sber_tech.data.getAddress.GetAddressRepoImpl
import ru.sber_tech.data.getAddress.GetAddressService
import ru.sber_tech.data.getAllMeets.AllMeetsDataSource
import ru.sber_tech.data.getAllMeets.AllMeetsRepositoryImpl
import ru.sber_tech.domain.addMeetScreen.AddMeetRepository
import ru.sber_tech.domain.getAddress.GetAddressRepo
import ru.sber_tech.domain.mainScreen.AllMeetsRepository

val dataModule = module {
    singleOf(::AllMeetsRepositoryImpl) { bind<AllMeetsRepository>() }
    singleOf(::AddMeetRepositoryImpl) { bind<AddMeetRepository>() }
    singleOf(::GetAddressRepoImpl) { bind<GetAddressRepo>() }
    singleOf(::AddMeetDataSource) { bind<AddMeetDataSource>() }
    singleOf(::AllMeetsDataSource) { bind<AllMeetsDataSource>() }
    singleOf(::GetAddressService) { bind<GetAddressService>() }
    single {
        HttpClient(OkHttp)
    } bind HttpClient::class
}