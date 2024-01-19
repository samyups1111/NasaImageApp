package com.example.nasaimageapp.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.nasaimageapp.model.GetNasaImagesUseCase
import com.example.nasaimageapp.model.NasaImagePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class NasaImageGridScreenViewModel @Inject constructor(
    private val getNasaImagesUseCase: GetNasaImagesUseCase,
) : ViewModel() {

    var searchBarQuery by mutableStateOf("")
        private set

    private var newSearchQuery = MutableStateFlow("")

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingDataFlow = newSearchQuery.flatMapLatest { query ->
        Pager(
            config = PagingConfig(
                pageSize = 100,
                prefetchDistance = 2,
            ),
            pagingSourceFactory = { NasaImagePagingSource(query, getNasaImagesUseCase) }
        )
            .flow
            .cachedIn(viewModelScope)
    }

    var nasaImageDetailsBottomSheetState by mutableStateOf<NasaImageDetailsBottomSheetState>(NasaImageDetailsBottomSheetState.HIDE)
        private set

    fun search(query: String = "nasa") {
        newSearchQuery.value = query
    }

    fun hideBottomSheet() {
        nasaImageDetailsBottomSheetState = NasaImageDetailsBottomSheetState.HIDE
    }

    fun showBottomSheet(
        title: String,
        url: String,
        description: String,
        photographer: String,
        location: String,
    ) {
        nasaImageDetailsBottomSheetState = NasaImageDetailsBottomSheetState.SHOW(
            title = title,
            url = url,
            description = description,
            photographer = photographer,
            location = location
        )
    }
    fun updateSearchBarQuery(query: String) {
        searchBarQuery = query
    }
}

sealed class NasaImageDetailsBottomSheetState {
    data class SHOW(
        val title: String,
        val url: String,
        val description: String,
        val photographer: String,
        val location: String,
    ) : NasaImageDetailsBottomSheetState()

    object HIDE : NasaImageDetailsBottomSheetState()
}