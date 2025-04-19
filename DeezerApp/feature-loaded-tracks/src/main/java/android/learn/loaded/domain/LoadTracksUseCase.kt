package android.learn.loaded.domain

import javax.inject.Inject

class LoadTracksUseCase @Inject constructor(
    private val repository: LoadedTracksRepository
) {
    suspend operator fun invoke() = repository.loadTracks()
}