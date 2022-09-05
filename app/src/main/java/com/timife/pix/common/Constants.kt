package com.timife.pix.common

import com.timife.pix.BuildConfig


object Constants {
    const val BASE_URL = "https://pixabay.com/api/"
    const val IMAGE_VIEW_TYPE = 2
    val IMAGE_TYPE = Pair("image_type", "photo")
    val KEY = Pair("key", BuildConfig.API_KEY)
    const val CACHE_NAME = "pix_cache"
    const val FIRST_PAGE = 1
    const val PAGE_SIZE = 25
    const val DEFAULT_SEARCH = "fruits"
}