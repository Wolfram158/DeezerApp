package android.learn.track.domain

import javax.inject.Inject

class PauseUseCase @Inject constructor(private val repository: TrackRepository) {
    operator fun invoke() = repository.pause()
}