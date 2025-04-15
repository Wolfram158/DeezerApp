package android.learn.data.data.mapper

import android.learn.data.data.network.dto.TrackDto
import android.learn.list.domain.Track

fun TrackDto.mapToTrack() = Track(
    id = this.id,
    title = this.title,
    duration = this.duration,
    linkToMp3 = this.linkToMp3,
    artistName = this.artist.name,
    albumImageLink = this.album.imageLink
)

fun List<TrackDto>.mapToTracks() = this.map { it.mapToTrack() }