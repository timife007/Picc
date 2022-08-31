package com.timife.pix.presentation.pix_list

import androidx.lifecycle.ViewModel
import com.timife.pix.domain.usecases.SearchPixUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PixListViewModel @Inject constructor(
    private val searchPixUseCase:SearchPixUseCase
) : ViewModel() {
    // TODO: Implement the ViewModel
}