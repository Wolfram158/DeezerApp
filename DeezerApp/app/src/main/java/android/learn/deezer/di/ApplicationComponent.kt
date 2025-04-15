package android.learn.deezer.di

import android.learn.deezer.App
import dagger.Component

@ApplicationScope
@Component(modules = [NetworkModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(app: App)
}