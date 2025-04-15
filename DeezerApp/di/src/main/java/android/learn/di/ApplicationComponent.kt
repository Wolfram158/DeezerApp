package android.learn.di

import dagger.Component

@ApplicationScope
@Component(modules = [NetworkModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(app: App)
}