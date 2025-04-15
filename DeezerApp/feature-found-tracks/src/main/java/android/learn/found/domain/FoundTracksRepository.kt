package android.learn.found.domain

import android.learn.list.domain.Track

interface FoundTracksRepository {
    suspend fun loadTracks(query: String? = null, limit: Int): List<Track>
}