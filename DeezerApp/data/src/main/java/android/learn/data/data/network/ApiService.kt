package android.learn.data.data.network

import android.learn.data.data.network.dto.ResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/chart")
    fun loadChart(@Query("limit") limit: Int = 15): ResponseDto

    @GET("/search")
    fun loadTracks(@Query("query") query: String, @Query("limit") limit: Int = 15): ResponseDto
}