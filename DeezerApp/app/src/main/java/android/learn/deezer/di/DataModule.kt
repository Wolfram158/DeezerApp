package android.learn.deezer.di

import android.learn.found.data.repository.FoundTracksRepositoryImpl
import android.learn.found.domain.FoundTracksRepository
import android.learn.track.data.TrackRepositoryImpl
import android.learn.track.domain.TrackRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @ApplicationScope
    @Binds
    fun bindFoundTrackRepository(impl: FoundTracksRepositoryImpl): FoundTracksRepository

    @ApplicationScope
    @Binds
    fun bindTrackRepository(impl: TrackRepositoryImpl): TrackRepository
}