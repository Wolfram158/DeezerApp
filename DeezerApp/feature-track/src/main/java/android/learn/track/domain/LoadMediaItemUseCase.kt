package android.learn.track.domain

import android.learn.list.domain.Track
import androidx.media3.common.MediaItem
import javax.inject.Inject

class LoadMediaItemUseCase @Inject constructor(
    private val repository: TrackRepository
) {
    suspend operator fun invoke(track: Track, callback: (MediaItem) -> Unit) =
        repository.loadMediaItem(track, callback)
}