package ru.uomkri.simbirtest.base

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
//import io.realm.Realm
import ru.uomkri.simbirtest.di.AppModule
import ru.uomkri.simbirtest.di.ApplicationComponent
import ru.uomkri.simbirtest.di.DaggerApplicationComponent
import ru.uomkri.simbirtest.di.ViewModelModule

class BaseApplication : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
            .schemaVersion(2)
            .deleteRealmIfMigrationNeeded()
            .build())
        appComponent = initDagger()
        appComponent.inject(this)

    }

    private fun initDagger() = DaggerApplicationComponent.builder()
            .appModule(AppModule(this))
            .build()


}