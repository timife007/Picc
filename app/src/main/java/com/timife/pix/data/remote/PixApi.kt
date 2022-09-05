package com.timife.pix.data.remote

import com.timife.pix.BuildConfig
import com.timife.pix.data.remote.dtos.PixResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixApi{
    @GET("/api/")
    suspend fun getPixImages(
        @Query("key") apiKey : String = BuildConfig.API_KEY,
        @Query("q") query:String="fruits",
        @Query("page") page:Int
    ): Response<PixResponse>
}