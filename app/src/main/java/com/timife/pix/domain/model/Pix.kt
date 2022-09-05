package com.timife.pix.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class Pix(
    val id: Int,
    val comments: Int,
    val downloads: Int,
    val previewUrl:String,
    val largeImageUrl: String,
    val likes: Int,
    val userName: String,
    val tags: String,
    var searchItem:String
): Parcelable
