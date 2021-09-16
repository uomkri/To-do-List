package ru.uomkri.simbirtest.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import io.realm.RealmConfiguration
import ru.uomkri.simbirtest.db.DbTask
import ru.uomkri.simbirtest.db.TaskDao
import ru.uomkri.simbirtest.screens.home.HomeViewModel
import ru.uomkri.simbirtest.utils.Utils
import javax.inject.Singleton
import io.realm.Realm
import ru.uomkri.simbirtest.screens.details.DetailsViewModel
import ru.uomkri.simbirtest.screens.newtask.NewTaskViewModel

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideUtils(): Utils {
        return Utils(application.applicationContext)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    @Provides
    @Singleton
    fun provideRealm(): Realm = Realm.getDefaultInstance()

    @Provides
    @Singleton
    fun provideDao(realm: Realm): TaskDao = TaskDao(realm)

}

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(vm: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(vm: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewTaskViewModel::class)
    abstract fun bindNewTaskViewModel(vm: NewTaskViewModel): ViewModel
}