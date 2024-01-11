package com.example.nasaimageapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nasaimageapp.model.NasaImage

@Composable
fun NasaImageGridScreen(
    viewModel: NasaImageGridScreenViewModel = hiltViewModel(),
) {
    when (viewModel.uiState) {
        is NasaImageGridScreenState.Success -> {
            NasaImageGrid(
                images = (viewModel.uiState as NasaImageGridScreenState.Success).images,
                onShowBottomSheet = { id: String -> viewModel.showBottomSheet(id) },
            )
            if (viewModel.bottomSheetState is BottomSheetState.SHOW) {
                NasaImageDetailsBottomSheet(
                    title = (viewModel.bottomSheetState as BottomSheetState.SHOW).title,
                    url = (viewModel.bottomSheetState as BottomSheetState.SHOW).url,
                    photographer = (viewModel.bottomSheetState as BottomSheetState.SHOW).photographer,
                    location = (viewModel.bottomSheetState as BottomSheetState.SHOW).location,
                    description = (viewModel.bottomSheetState as BottomSheetState.SHOW).description,
                    onBack = { viewModel.hideBottomSheet() },
                )

            }
        }
        is NasaImageGridScreenState.Loading -> {
            Text(text = "Fetching data. Please wait...")
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NasaImageGrid(
    images: List<NasaImage>,
    onShowBottomSheet: (id: String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ) {
        items(images) { nasaImageData ->
            GlideImage(
                model = nasaImageData.url,
                contentDescription = "Nasa image",
                modifier = Modifier
                    .clickable(onClick = {
                        onShowBottomSheet(
                            nasaImageData.id
                        )
                    })
                )
        }
    }
}