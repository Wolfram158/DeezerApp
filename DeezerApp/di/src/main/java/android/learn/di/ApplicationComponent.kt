package android.learn.di

import android.learn.found.presentation.FoundTracksFragment
import dagger.Component

@ApplicationScope
@Component(modules = [NetworkModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(app: App)

    fun inject(fragment: FoundTracksFragment)
}