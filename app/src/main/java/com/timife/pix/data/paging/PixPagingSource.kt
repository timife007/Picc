package com.timife.pix.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.timife.pix.common.Constants.FIRST_PAGE
import com.timife.pix.data.mappers.toPix
import com.timife.pix.data.remote.PixApi
import com.timife.pix.domain.model.Pix
import java.io.IOException

class PixPagingSource (  private val searchString: String, private val pixApi: PixApi ) :
    PagingSource<Int, Pix>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pix> {
        val page = params.key ?: FIRST_PAGE
        return try {
            val data = pixApi.getPixImages(query = searchString, page = page).body()?.pics?.map { it.toPix() } ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (t: Throwable) {
            var exception = t
            if (t is IOException) {
                exception = IOException("Please check your internet connection and try again")
            }

            LoadResult.Error(exception)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Pix>): Int? {
        return state.anchorPosition
    }
}