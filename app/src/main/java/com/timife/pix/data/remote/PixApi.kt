package com.timife.pix.data.remote

import com.timife.pix.BuildConfig
import com.timife.pix.data.remote.dtos.PixResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixApi{
    @GET("")
    suspend fun getPixImages(
        @Query("apiKey") apiKey : String = BuildConfig.API_KEY,
        @Query("q") query:String = "Fruits",
        @Query("page") page:Int
    ): Response<PixResponse>
}