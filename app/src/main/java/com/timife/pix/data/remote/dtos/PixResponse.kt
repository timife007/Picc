package com.timife.pix.data.remote.dtos


import com.squareup.moshi.Json

data class PixResponse(
    @field:Json(name = "hits")
    val pics: List<PixDto>,
    val total: Int,
    val totalHits: Int
)