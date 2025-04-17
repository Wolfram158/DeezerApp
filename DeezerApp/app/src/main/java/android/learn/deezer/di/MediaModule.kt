package android.learn.deezer.di

import android.app.Application
import android.content.ComponentName
import android.learn.track.data.MediaService
import android.util.Log
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.google.common.util.concurrent.ListenableFuture
import dagger.Module
import dagger.Provides

@Module
class MediaModule {
    @ApplicationScope
    @Provides
    fun provideSessionToken(application: Application): SessionToken {
        return SessionToken(application, ComponentName(application, MediaService::class.java))
    }

    @ApplicationScope
    @Provides
    fun provideControllerFuture(
        application: Application,
        sessionToken: SessionToken
    ): ListenableFuture<MediaController> {
        return MediaController.Builder(application, sessionToken).buildAsync()
    }

}