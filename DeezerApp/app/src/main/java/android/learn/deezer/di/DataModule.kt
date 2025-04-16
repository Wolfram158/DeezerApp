package android.learn.deezer.di

import android.learn.found.data.repository.FoundTracksRepositoryImpl
import android.learn.found.domain.FoundTracksRepository
import dagger.Binds
import dagger.Module

@Module
interface DataModule {
    @Binds
    fun bindFoundTrackRepository(impl: FoundTracksRepositoryImpl): FoundTracksRepository
}