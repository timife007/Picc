package com.timife.pix.presentation.pix_list

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.timife.pix.domain.model.Pix
import com.timife.pix.domain.usecases.SearchPixUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PixListViewModel @Inject constructor(
    private val searchPixUseCase: SearchPixUseCase
) : ViewModel() {
    companion object {
        private const val DEFAULT_QUERY = "fruits"
    }

    private val currentQuery = MutableLiveData(
        DEFAULT_QUERY
    )

    val pixList = currentQuery.switchMap { queryString ->
        searchPixUseCase.invoke(queryString).cachedIn(viewModelScope).asLiveData()
    }

    private val _navigateToSelectedItem = MutableLiveData<Pix?>()
    val navigateToSelectedItem: LiveData<Pix?>
        get() = _navigateToSelectedItem


    fun getSearchPix(searchQuery: String) {
        currentQuery.value = searchQuery
    }

    fun displayImages(pix: Pix) {
        _navigateToSelectedItem.value = pix
    }

    //To end navigation, and prompt navigateUp.
    fun displayImagesComplete() {
        _navigateToSelectedItem.value = null
    }


}