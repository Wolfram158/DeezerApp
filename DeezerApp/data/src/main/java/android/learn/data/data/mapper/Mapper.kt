package android.learn.data.data.mapper

import android.learn.data.data.network.dto.TrackDto
import android.learn.list.domain.Track

fun TrackDto.mapToTrack() = Track(
    id = id,
    album = album.title,
    title = title,
    duration = duration,
    linkToMp3 = linkToMp3,
    artistName = artist.name,
    albumImageLink = album.imageLink
)

fun List<TrackDto>.mapToTracks() = map { it.mapToTrack() }