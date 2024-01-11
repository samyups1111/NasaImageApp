package com.example.nasaimageapp.view

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasaimageapp.model.NasaImage
import com.example.nasaimageapp.model.NasaImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NasaImageGridScreenViewModel @Inject constructor(
    private val nasaImageRepository: NasaImageRepository
) : ViewModel() {

    init {
        getNasaImages()
    }

    var uiState by mutableStateOf<NasaImageGridScreenState>(NasaImageGridScreenState.Loading)
        private set

    var bottomSheetState by mutableStateOf<BottomSheetState>(BottomSheetState.HIDE)
        private set

    private fun getNasaImages() {
        viewModelScope.launch {
            val nasaImages = nasaImageRepository.getImagesFromNetwork()
            uiState = NasaImageGridScreenState.Success(nasaImages)
        }
    }

    fun hideBottomSheet() {
        bottomSheetState = BottomSheetState.HIDE
    }

    fun showBottomSheet(id: String) {
        val selectedImage =
            (uiState as NasaImageGridScreenState.Success).images.first { it.id == id }
        bottomSheetState = BottomSheetState.SHOW(
            title = selectedImage.title,
            url = selectedImage.url,
            description = selectedImage.description,
            photographer = selectedImage.photographer,
            location = selectedImage.location,
        )
    }
}

sealed class NasaImageGridScreenState {
    object Loading : NasaImageGridScreenState()

    data class Success(
        val images: List<NasaImage>
    ) : NasaImageGridScreenState()
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