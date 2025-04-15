package android.learn.data.data.network.dto

import android.learn.utils.Seconds
import com.google.gson.annotations.SerializedName

data class TrackDto(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("duration") val duration: Seconds,
    @SerializedName("preview") val linkToMp3: String,
    @SerializedName("artist") val artist: ArtistDto,
    @SerializedName("album") val album: AlbumDto
)
