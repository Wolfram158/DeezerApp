package android.learn.loaded.domain

import android.learn.list.domain.Track

interface LoadedTracksRepository {
    suspend fun loadTracks(): List<Track>
}