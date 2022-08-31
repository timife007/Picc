package com.timife.pix.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pixListEntity")
data class Pix(
    @PrimaryKey val id: Int,
    val comments: Int,
    val downloads: Int,
    val previewUrl:String,
    val largeImageUrl: String,
    val likes: Int,
    val userName: String,
    val tags: String,
    val searchItem:String
)
