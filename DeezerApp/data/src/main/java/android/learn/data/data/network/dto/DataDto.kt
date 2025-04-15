package android.learn.data.data.network.dto

import com.google.gson.annotations.SerializedName

data class DataDto(
    @SerializedName("data") val data: List<TrackDto>
)
