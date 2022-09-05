package com.timife.pix.data.repositories

import androidx.paging.*
import com.timife.pix.common.Constants.PAGE_SIZE
import com.timife.pix.data.paging.PixPagingSource
import com.timife.pix.data.remote.PixApi
import com.timife.pix.domain.model.Pix
import com.timife.pix.domain.repositories.PixRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixRepositoryImpl @Inject constructor(private val pixApi: PixApi) : PixRepository {
    override fun searchPixList(query: String): Flow<PagingData<Pix>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                PixPagingSource(query, pixApi)
            }
        ).flow
    }
}