package android.learn.track.presentation

import android.learn.list.domain.Track
import android.learn.track.domain.LoadMediaItemUseCase
import android.learn.track.domain.PauseUseCase
import android.learn.track.domain.PlayUseCase
import android.learn.track.domain.SaveLocallyUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrackViewModel @Inject constructor(
    private val playUseCase: PlayUseCase,
    private val pauseUseCase: PauseUseCase,
    private val loadMediaItemUseCase: LoadMediaItemUseCase,
    private val saveLocallyUseCase: SaveLocallyUseCase
) : ViewModel() {
    fun loadMediaItem(track: Track, callback: (MediaItem) -> Unit) {
        viewModelScope.launch {
            loadMediaItemUseCase(track, callback)
        }
    }

    fun saveLocally(track: Track) {
        viewModelScope.launch {
            saveLocallyUseCase(track)
        }
    }
}
