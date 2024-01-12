package com.example.nasaimageapp.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.nasaimageapp.model.GetNasaImagesUseCase
import com.example.nasaimageapp.model.NasaImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NasaImageGridScreenViewModel @Inject constructor(
    private val getNasaImagesUseCase: GetNasaImagesUseCase,
) : ViewModel() {

    var imageListState: MutableStateFlow<PagingData<NasaImage>> = MutableStateFlow(PagingData.empty())
        private set

    var searchQuery by mutableStateOf("search...")
        private set

    var bottomSheetState by mutableStateOf<BottomSheetState>(BottomSheetState.HIDE)
        private set

    init {
        onLoad()
    }

    fun onLoad(query: String = "nasa") {
        viewModelScope.launch {
            getNasaImages(query)
        }
    }

    private suspend fun getNasaImages(query: String) {
        getNasaImagesUseCase.invoke(query)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                imageListState.value = it
            }
    }

    fun hideBottomSheet() {
        bottomSheetState = BottomSheetState.HIDE
    }

    fun showBottomSheet(
        title: String,
        url: String,
        description: String,
        photographer: String,
        location: String,
    ) {
        bottomSheetState = BottomSheetState.SHOW(
            title = title,
            url = url,
            description = description,
            photographer = photographer,
            location = location
        )
    }
    fun onUpdateQuery(query: String) {
        searchQuery = query
    }

    fun onActiveChange(isActive: Boolean) {
        if (isActive) searchQuery = ""
    }
}

sealed class BottomSheetState {
    data class SHOW(
        val title: String,
        val url: String,
        val description: String,
        val photographer: String,
        val location: String,
    ) : BottomSheetState()

    object HIDE : BottomSheetState()
}