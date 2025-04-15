package android.learn.found.domain

import javax.inject.Inject

class LoadTracksUseCase @Inject constructor(
    private val foundTracksRepository: FoundTracksRepository
) {
    suspend operator fun invoke(query: String?, limit: Int) =
        foundTracksRepository.loadTracks(query, limit)
}