package android.learn.found.domain

interface FoundTracksRepository {
    suspend fun loadTracks(query: String? = null, limit: Int): List<Track>
}