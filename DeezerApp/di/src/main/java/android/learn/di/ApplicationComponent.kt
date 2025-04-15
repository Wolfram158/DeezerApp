package android.learn.di

import dagger.Component

@ApplicationScope
@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(app: App)
}