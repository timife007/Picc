package com.timife.pix.domain.usecases

import androidx.paging.PagingData
import com.timife.pix.domain.model.Pix
import com.timife.pix.domain.repositories.PixRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchPixUseCase @Inject constructor(
    private val repository: PixRepository
){
    operator fun invoke(query:String): Flow<PagingData<Pix>> {
        return repository.searchPixList(query)
    }
}