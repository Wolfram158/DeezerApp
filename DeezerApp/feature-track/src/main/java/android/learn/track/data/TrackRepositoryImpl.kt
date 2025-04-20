package android.learn.track.data

import android.app.Application
import android.content.ContentValues
import android.graphics.Bitmap
import android.learn.list.R
import android.learn.list.domain.Track
import android.learn.track.domain.TrackRepository
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(
    private val imageLoader: ImageLoader,
    private val imageRequestBuilder: ImageRequest.Builder,
    private val application: Application,
    private val client: OkHttpClient
) : TrackRepository {
    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun play() {
        TODO("Not yet implemented")
    }

    override suspend fun loadMediaItem(track: Track, callback: (MediaItem) -> Unit) {
        MediaItem.Builder()
            .setMediaMetadata(
                MediaMetadata.Builder()
                    .setArtist(track.artistName)
                    .setAlbumTitle(track.album)
                    .setArtworkData(
                        getImageBytes(track.albumImageLink, true),
                        MediaMetadata.PICTURE_TYPE_MEDIA
                    )
                    .setTitle(track.title)
                    .build()
            )
            .setMediaId("${track.linkToMp3.toUri()}")
            .build().also { callback(it) }
    }

    private suspend fun <T> getImageBytes(albumImageSource: T, chance: Boolean): ByteArray {
        val request = imageRequestBuilder
            .data(albumImageSource)
            .build()
        val bitmap = when (val result = imageLoader.execute(request)) {
            is ErrorResult -> result.drawable?.toBitmap()
                ?: return if (chance) {
                    getImageBytes(R.drawable.ic_launcher_background, false)
                } else {
                    throw RuntimeException("Unexpected error")
                }

            is SuccessResult -> result.drawable.toBitmap()
        }
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    override suspend fun saveLocally(track: Track) {
        withContext(Dispatchers.IO) {
            insertData(track)?.let { uri ->
                getAudioBytes(track.linkToMp3).let { bytes ->
                    application.contentResolver.openOutputStream(uri).use {
                        (it ?: throw RuntimeException("Unexpected error")).write(bytes)
                    }
                }
            }
        }
    }

    private fun getAudioBytes(link: String): ByteArray? {
        val request = Request.Builder()
            .url(link)
            .build()
        return try {
            val response: Response = client.newCall(request).execute()
            if (response.isSuccessful) {
                response.body?.bytes()
            } else {
                null
            }
        } catch (_: Exception) {
            null
        }
    }

    private fun insertData(track: Track): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.Audio.Media.DISPLAY_NAME, "${track.title}.mp3")
            put(MediaStore.Audio.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
            put(MediaStore.Audio.Media.RELATIVE_PATH, Environment.DIRECTORY_MUSIC)
            put(MediaStore.Audio.Media.TITLE, track.title)
            put(MediaStore.Audio.Media.ARTIST, track.artistName)
            put(MediaStore.Audio.Media.ALBUM, track.album)
        }
        return application.contentResolver.insert(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL_PRIMARY
                )
            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }, contentValues
        )
    }
}