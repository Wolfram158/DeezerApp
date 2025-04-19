package android.learn.deezer.di

import android.learn.found.presentation.FoundTracksViewModel
import android.learn.loaded.presentation.LoadedTracksViewModel
import android.learn.track.presentation.TrackViewModel
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FoundTracksViewModel::class)
    fun bindTracksViewModel(viewModel: FoundTracksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrackViewModel::class)
    fun bindTrackViewModel(viewModel: TrackViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoadedTracksViewModel::class)
    fun bindLoadedTracksViewModel(viewModel: LoadedTracksViewModel): ViewModel
}