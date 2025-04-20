package android.learn.loaded.data

import android.app.Application
import android.database.Cursor
import android.learn.list.domain.Track
import android.learn.loaded.domain.LoadedTracksRepository
import android.os.Build
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class LoadedTracksRepositoryImpl @Inject constructor(
    private val application: Application
) : LoadedTracksRepository {
    override suspend fun loadTracks(): List<Track> {
        return withContext(Dispatchers.IO) {
            val tracks = mutableListOf<Track>()
            val uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL_PRIMARY
                )
            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
            val projection = arrayOf(
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION
            )
            val cursor = application.contentResolver.query(
                uri,
                projection,
                null,
                null,
                null
            )
            cursor?.use { cur ->
                val dataColumn = cur.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
                while (cur.moveToNext()) {
                    val filePath = cur.getString(dataColumn)
                    val artist = extractValue(
                        cur,
                        DEFAULT_STRING,
                        MediaStore.Audio.Media.ARTIST
                    ) { cursor.getString(it) }
                    val title = extractValue(
                        cur,
                        DEFAULT_STRING,
                        MediaStore.Audio.Media.TITLE
                    ) { cursor.getString(it) }
                    val album = extractValue(
                        cur,
                        DEFAULT_STRING,
                        MediaStore.Audio.Media.ALBUM
                    ) { cursor.getString(it) }
                    val duration = extractValue(
                        cur,
                        DEFAULT_LONG,
                        MediaStore.Audio.Media.DURATION
                    ) { cursor.getLong(it) }
                    tracks.add(
                        Track(
                            id = Random.nextLong(),
                            album = album,
                            title = title,
                            duration = duration,
                            linkToMp3 = filePath,
                            artistName = artist,
                            albumImageLink = "",
                        )
                    )
                }
            }

            tracks
        }
    }

    override suspend fun filterTracks(tracks: List<Track>, template: String): List<Track> {
        return withContext(Dispatchers.Default) {
            tracks.filter {
                listOf(it.title, it.album, it.artistName).map { it1 -> it1.lowercase() }
                    .any { it2 -> it2.contains(template.lowercase()) }
            }
        }
    }

    private fun <T> extractValue(
        cursor: Cursor,
        default: T,
        target: String,
        block: (Int) -> T?
    ): T {
        return cursor.getColumnIndex(target)
            .let {
                when (it) {
                    -1 -> default
                    else -> {
                        block(it) ?: default
                    }
                }
            }
    }

    companion object {
        private const val DEFAULT_STRING = "Undefined"
        private const val DEFAULT_LONG = 0L
    }
}