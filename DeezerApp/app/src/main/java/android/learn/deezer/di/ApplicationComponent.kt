package android.learn.deezer.di

import android.app.Application
import android.learn.deezer.presentation.App
import android.learn.deezer.presentation.FoundTracksFragment
import android.learn.deezer.presentation.LoadedTracksFragment
import android.learn.deezer.presentation.TrackFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NetworkModule::class,
        DataModule::class,
        ViewModelModule::class,
        MediaModule::class,
        UtilsModule::class
    ]
)
interface ApplicationComponent {
    fun inject(app: App)

    fun inject(fragment: FoundTracksFragment)

    fun inject(fragment: TrackFragment)

    fun inject(fragment: LoadedTracksFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}