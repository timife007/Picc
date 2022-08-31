package com.timife.pix.data.mappers

import com.timife.pix.data.remote.dtos.PixDto
import com.timife.pix.domain.model.Pix

fun PixDto.toPix(): Pix {
    return Pix(
        id = id,
        userName = user,
        previewUrl = previewUrl,
        largeImageUrl = largeImageUrl,
        likes = likes,
        downloads = downloads,
        tags = tags,
        comments = comments,
        searchItem = searchItem ?: ""
    )
}