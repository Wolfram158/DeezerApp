package android.learn.deezer.di

import android.app.Application
import android.learn.deezer.App
import android.learn.deezer.FoundTracksFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NetworkModule::class,
        DataModule::class,
        ViewModelModule::class,
        MediaModule::class]
)
interface ApplicationComponent {
    fun inject(app: App)

    fun inject(fragment: FoundTracksFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}