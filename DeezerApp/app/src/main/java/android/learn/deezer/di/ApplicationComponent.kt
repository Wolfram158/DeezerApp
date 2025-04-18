package android.learn.deezer.di

import android.app.Application
import android.learn.deezer.App
import android.learn.deezer.FoundTracksFragment
import android.learn.deezer.TrackFragment
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

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}