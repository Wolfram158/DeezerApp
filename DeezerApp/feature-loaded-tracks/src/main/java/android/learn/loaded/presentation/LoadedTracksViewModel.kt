package android.learn.loaded.presentation

import android.learn.loaded.domain.LoadTracksUseCase
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LoadedTracksViewModel @Inject constructor(
    private val loadTracksUseCase: LoadTracksUseCase
) : ViewModel() {
    suspend fun loadTracks() = loadTracksUseCase()
}