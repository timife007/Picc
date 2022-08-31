package com.timife.pix.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.timife.pix.BuildConfig
import com.timife.pix.data.local.database.PixDb
import com.timife.pix.data.local.model.PixRemoteKeys
import com.timife.pix.data.mappers.toPix
import com.timife.pix.data.remote.PixApi
import com.timife.pix.domain.model.Pix

@OptIn(ExperimentalPagingApi::class)
class PixRemoteMediator(
    private val pixApi: PixApi,
    private val pixDb: PixDb,
    private val query: String
) :
    RemoteMediator<Int, Pix>() {

    private val pixDao = pixDb.pixDao()
    private val pixRemoteKeysDao = pixDb.pixRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Pix>): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                }
            }
            val response =
                pixApi.getPixImages(apiKey = BuildConfig.API_KEY, page = page, query = query)
            var endOfPaginationReached = false
            if (response.isSuccessful) {
                val responseData = response.body()

                endOfPaginationReached = responseData == null
                responseData?.let {
                    it.pics.map { pixDto ->
                        pixDto.searchItem = query
                    }

                    pixDb.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            pixDao.clearPixList()
                            pixRemoteKeysDao.deleteAllPixRemoteKeys()
                        }
                        val prevPage: Int? = if (page <= 1) null else page - 1
                        val nextPage: Int = page + 1


                        val keys = responseData.pics.map { pic ->
                            PixRemoteKeys(
                                id = pic.id,
                                prevPage = prevPage,
                                nextPage = nextPage,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }
                        pixRemoteKeysDao.addAllPixRemoteKeys(pixRemoteKeys = keys)
                        pixDao.insertPixListEntity(pixListEntity = responseData.pics.map { pixDto ->
                            pixDto.toPix()
                        })
                    }
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Pix>,
    ): PixRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                pixRemoteKeysDao.getPixRemoteKeys(pixId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Pix>,
    ): PixRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pix ->
                pixRemoteKeysDao.getPixRemoteKeys(pixId = pix.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Pix>,
    ): PixRemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pix ->
                pixRemoteKeysDao.getPixRemoteKeys(pixId = pix.id)
            }
    }
}