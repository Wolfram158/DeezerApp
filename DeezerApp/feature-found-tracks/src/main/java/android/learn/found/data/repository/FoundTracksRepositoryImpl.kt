package android.learn.found.data.repository

import android.learn.data.data.network.ApiService
import android.learn.deezer.data.mapper.mapToTracks
import android.learn.deezer.data.network.ApiService
import android.learn.found.domain.Track
import android.learn.found.domain.FoundTracksRepository
import javax.inject.Inject

class FoundTracksRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : FoundTracksRepository {
    override suspend fun loadTracks(query: String?, limit: Int): List<Track> {
        if (query == null) {
            return apiService.loadChart(limit).tracks.data.mapToTracks()
        }
        return apiService.loadTracks(query, limit).tracks.data.mapToTracks()
    }
}