package android.learn.data.data.network.dto

import com.google.gson.annotations.SerializedName

data class AlbumDto(
    @SerializedName("title") val title: String,
    @SerializedName("cover") val imageLink: String
)
