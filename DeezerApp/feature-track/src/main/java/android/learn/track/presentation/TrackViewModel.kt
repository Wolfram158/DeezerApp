package android.learn.track.presentation

import android.graphics.Bitmap
import android.learn.list.domain.Track
import android.learn.track.domain.PauseUseCase
import android.learn.track.domain.PlayUseCase
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class TrackViewModel @Inject constructor(
    private val playUseCase: PlayUseCase,
    private val pauseUseCase: PauseUseCase,
    private val imageLoader: ImageLoader,
    private val imageRequestBuilder: ImageRequest.Builder
) : ViewModel() {
    suspend fun loadMediaItem(track: Track, callback: (MediaItem) -> Unit) {
        val request = imageRequestBuilder
            .data(track.albumImageLink)
            .build()
        val result = imageLoader.execute(request)
        var bytes: ByteArray? = null
        if (result is SuccessResult) {
            val bitmap = result.drawable.toBitmap()
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            bytes = stream.toByteArray()
        }
        val newItem = MediaItem.Builder()
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist(track.artistName)
                    .setAlbumTitle(track.album)
                    .setArtworkData(bytes, MediaMetadata.PICTURE_TYPE_MEDIA)
                    .setTitle(track.title)
                    .build()
            )
            .setMediaId("${track.linkToMp3.toUri()}")
            .build()
        callback(newItem)
    }
}