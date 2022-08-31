package com.timife.pix.data.repositories

import androidx.paging.*
import com.timife.pix.data.local.database.PixDao
import com.timife.pix.data.local.database.PixDb
import com.timife.pix.data.paging.PixRemoteMediator
import com.timife.pix.data.remote.PixApi
import com.timife.pix.domain.model.Pix
import com.timife.pix.domain.repositories.PixRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PixRepositoryImpl @Inject constructor(private val pixApi: PixApi,private val pixDb: PixDb) : PixRepository {
    private val pixDao = pixDb.pixDao()
    @OptIn(ExperimentalPagingApi::class)
    override fun searchPixList(query: String): Flow<PagingData<Pix>> {
        val pagingSourceFactory = { pixDao.searchPixListEntity(query) }
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = PixRemoteMediator(
                pixApi,
                pixDb,
                query
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }
}