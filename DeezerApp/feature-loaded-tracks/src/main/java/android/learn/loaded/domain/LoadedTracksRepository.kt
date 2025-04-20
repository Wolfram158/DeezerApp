package android.learn.loaded.domain

import android.learn.list.domain.Track

interface LoadedTracksRepository {
    suspend fun loadTracks(): List<Track>

    suspend fun filterTracks(tracks: List<Track>, template: String): List<Track>
}