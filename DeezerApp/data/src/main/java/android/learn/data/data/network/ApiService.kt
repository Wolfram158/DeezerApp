package android.learn.data.data.network

import android.learn.data.data.network.dto.DataDto
import android.learn.data.data.network.dto.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Query
import androidx.annotation.IntRange

interface ApiService {
    @GET("/chart")
    suspend fun loadChart(@Query("limit") @IntRange(1) limit: Int = 15): ResponseDto

    @GET("/search")
    suspend fun loadTracks(
        @Query("q") query: String,
        @Query("limit") @IntRange(1) limit: Int = 15
    ): DataDto
}