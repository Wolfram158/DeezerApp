package android.learn.loaded.domain

import android.learn.list.domain.Track
import javax.inject.Inject

class FilterTracksUseCase @Inject constructor(
    private val repository: LoadedTracksRepository
) {
    suspend operator fun invoke(tracks: List<Track>, template: String) =
        repository.filterTracks(tracks, template)
}