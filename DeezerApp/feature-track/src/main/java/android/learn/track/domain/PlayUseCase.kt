package android.learn.track.domain

import javax.inject.Inject

class PlayUseCase @Inject constructor(private val repository: TrackRepository) {
    operator fun invoke() = repository.play()
}