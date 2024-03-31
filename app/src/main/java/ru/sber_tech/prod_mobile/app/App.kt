package ru.sber_tech.prod_mobile.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import ru.sber_tech.data.di.dataModule
import ru.sber_tech.domain.di.domainModule
import ru.sber_tech.prod_mobile.di.appModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule, dataModule, domainModule
                )
            )
        }
    }
}