package android.learn.found.presentation

import android.learn.found.domain.LoadTracksUseCase
import android.learn.list.domain.Track
import android.learn.utils.Error
import android.learn.utils.Progress
import android.learn.utils.Result
import android.learn.utils.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FoundTracksViewModel @Inject constructor(
    private val loadTracksUseCase: LoadTracksUseCase
) : ViewModel() {
    private val _tracksState: MutableStateFlow<State<List<Track>>> = MutableStateFlow(Progress())
    val tracksState: StateFlow<State<List<Track>>>
        get() = _tracksState

    fun loadTrack(query: String?, limit: Int) {
        viewModelScope.launch {
            _tracksState.emit(Progress())
            try {
                val tracks = loadTracksUseCase(query = query, limit = limit)
                _tracksState.emit(Result(tracks))
            } catch (_: Exception) {
                _tracksState.emit(Error())
            }
        }
    }

    fun loadChart(limit: Int) = loadTrack(query = null, limit = limit)
}