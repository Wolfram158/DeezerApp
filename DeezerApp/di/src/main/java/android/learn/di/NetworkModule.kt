package android.learn.di

import android.learn.data.data.network.ApiFactory
import android.learn.data.data.network.ApiService
import dagger.Module
import dagger.Provides

@Module
interface NetworkModule {
    companion object {
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}