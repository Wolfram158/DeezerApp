package android.learn.track.domain

import android.learn.list.domain.Track
import javax.inject.Inject

class SaveLocallyUseCase @Inject constructor(
    private val repository: TrackRepository
){
    suspend operator fun invoke(track: Track) = repository.saveLocally(track)
}