package ru.uomkri.simbirtest.di

import dagger.Component
import ru.uomkri.simbirtest.base.BaseApplication
import ru.uomkri.simbirtest.screens.details.DetailsFragment
import ru.uomkri.simbirtest.screens.home.HomeFragment
import ru.uomkri.simbirtest.screens.newtask.NewTaskFragment
import javax.inject.Singleton

@Singleton
@Component(
        modules = [AppModule::class, ViewModelModule::class]
)
interface ApplicationComponent {
    fun inject(application: BaseApplication)
    fun inject(homeFragment: HomeFragment)
    fun inject(detailsFragment: DetailsFragment)
    fun inject(newTaskFragment: NewTaskFragment)
}
