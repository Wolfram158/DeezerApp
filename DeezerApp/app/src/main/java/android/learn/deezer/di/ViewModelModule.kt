package android.learn.deezer.di

import android.learn.found.presentation.FoundTracksViewModel
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FoundTracksViewModel::class)
    fun bindWeatherViewModel(viewModel: FoundTracksViewModel): ViewModel
}