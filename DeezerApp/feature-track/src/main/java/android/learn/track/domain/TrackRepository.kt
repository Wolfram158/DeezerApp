package android.learn.track.domain

import android.learn.list.domain.Track
import androidx.media3.common.MediaItem

interface TrackRepository {
    fun pause()

    fun play()

    suspend fun loadMediaItem(track: Track, callback: (MediaItem) -> Unit)

    suspend fun saveLocally(track: Track)
}