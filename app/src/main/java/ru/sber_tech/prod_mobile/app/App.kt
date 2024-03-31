package ru.sber_tech.prod_mobile.app

import android.app.Application
import com.yandex.mapkit.MapKitFactory
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext
import ru.sber_tech.data.di.dataModule
import ru.sber_tech.domain.di.domainModule
import ru.sber_tech.prod_mobile.di.appModule

class App : Application() {
    var isInit = false
    override fun onCreate() {
        super.onCreate()
        if (!isInit){
            MapKitFactory.setApiKey("6c6cd304-9d0b-4a28-a718-27e056899465")
            MapKitFactory.initialize(this@App)
            isInit = true
        }
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