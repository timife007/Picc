package com.timife.pix.domain.repositories

import androidx.paging.PagingData
import com.timife.pix.domain.model.Pix
import kotlinx.coroutines.flow.Flow

interface PixRepository {
    fun searchPixList(query:String): Flow<PagingData<Pix>>
}