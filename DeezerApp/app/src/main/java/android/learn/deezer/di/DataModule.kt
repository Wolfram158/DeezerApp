package android.learn.deezer.di

import android.learn.found.data.repository.FoundTracksRepositoryImpl
import android.learn.found.domain.FoundTracksRepository
import android.learn.loaded.data.LoadedTracksRepositoryImpl
import android.learn.loaded.domain.LoadedTracksRepository
import android.learn.track.data.TrackRepositoryImpl
import android.learn.track.domain.TrackRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindFoundTracksRepository(impl: FoundTracksRepositoryImpl): FoundTracksRepository

    @ApplicationScope
    @Binds
    fun bindTrackRepository(impl: TrackRepositoryImpl): TrackRepository

    @ApplicationScope
    @Binds
    fun bindLoadedTracksRepository(impl: LoadedTracksRepositoryImpl): LoadedTracksRepository
}