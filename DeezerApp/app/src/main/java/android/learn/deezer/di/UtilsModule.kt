package android.learn.deezer.di

import android.app.Application
import coil.ImageLoader
import coil.request.ImageRequest
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {
    @Provides
    fun provideImageLoader(application: Application): ImageLoader {
        return ImageLoader(application)
    }

    @Provides
    fun provideImageRequestBuilder(application: Application): ImageRequest.Builder {
        return ImageRequest.Builder(application)
    }
}