package android.learn.data.data.network.dto

import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("tracks") val tracks: DataDto
)
