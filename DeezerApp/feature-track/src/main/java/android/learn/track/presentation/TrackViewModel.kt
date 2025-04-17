package android.learn.track.presentation

import android.learn.track.domain.PauseUseCase
import android.learn.track.domain.PlayUseCase
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class TrackViewModel @Inject constructor(
    private val playUseCase: PlayUseCase,
    private val pauseUseCase: PauseUseCase
): ViewModel() {
}