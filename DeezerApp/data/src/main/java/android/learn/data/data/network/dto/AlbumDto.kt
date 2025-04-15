package android.learn.data.data.network.dto

import com.google.gson.annotations.SerializedName

data class AlbumDto(
    @SerializedName("cover") val imageLink: String
)
