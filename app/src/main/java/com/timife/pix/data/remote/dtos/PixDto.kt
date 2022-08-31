package com.timife.pix.data.remote.dtos

import com.squareup.moshi.Json

data class PixDto(
    val collections: Int,
    val comments: Int,
    val downloads: Int,
    val id: Int,
    val imageHeight: Int,
    val imageSize: Int,
    val imageWidth: Int,
    @field:Json(name = "largeImageURL")
    val largeImageUrl: String,
    val likes: Int,
    val pageURL: String,
    val previewHeight: Int,
    @field:Json(name = "previewURL")
    val previewUrl: String,
    val previewWidth: Int,
    val tags: String,
    val type: String,
    val user: String,
    @Json(name = "user_id")
    val userId: Int,
    val userImageURL: String,
    val views: Int,
    @field:Json(name = "webformatHeight")
    val webFormatHeight: Int,
    @field:Json(name = "webformatURL")
    val webFormatUrl: String,
    @field:Json(name = "webformatWidth")
    val webFormatWidth: Int,
    var searchItem:String? = null
)
