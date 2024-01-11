package com.example.nasaimageapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nasaimageapp.model.NasaImage

@Composable
fun NasaImageGridScreen(
    modifier: Modifier = Modifier,
    viewModel: NasaImageGridScreenViewModel = hiltViewModel(),
) {
    when (viewModel.uiState) {
        is NasaImageGridScreenState.Success -> {
            NasaImageGrid(
                images = (viewModel.uiState as NasaImageGridScreenState.Success).images,
                onShowBottomSheet = { id: String -> viewModel.showBottomSheet(id) },
                modifier = modifier,
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
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxSize(),
    ) {
        items(images) { nasaImageData ->
            GlideImage(
                model = nasaImageData.url,
                contentDescription = "Nasa image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable(onClick = {
                        onShowBottomSheet(
                            nasaImageData.id
                        )
                    })
                    .size(100.dp)
                )
        }
    }
}