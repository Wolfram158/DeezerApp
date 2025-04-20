package android.learn.loaded.presentation

import android.learn.list.domain.Track
import android.learn.loaded.domain.FilterTracksUseCase
import android.learn.loaded.domain.LoadTracksUseCase
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LoadedTracksViewModel @Inject constructor(
    private val loadTracksUseCase: LoadTracksUseCase,
    private val filterTracksUseCase: FilterTracksUseCase
) : ViewModel() {
    private var allTracks: List<Track> = emptyList()

    sealed class Content {
        class Tracks(val tracks: List<Track>) : Content()

        class Empty : Content()
    }

    private var _content: Content = Content.Empty()
    val content get() = _content

    suspend fun loadTracks(): List<Track> {
        return loadTracksUseCase().also { allTracks = it }
    }

    suspend fun filterTracks(template: String): List<Track> {
        if (template.trim() == "") {
            return loadTracks().also { allTracks = it }
        }
        return filterTracksUseCase(allTracks, template.trim()).also {
            _content =
                Content.Tracks(it)
        }
    }
}