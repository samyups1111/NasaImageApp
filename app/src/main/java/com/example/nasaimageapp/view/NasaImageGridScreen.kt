package com.example.nasaimageapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nasaimageapp.model.NasaImage

@Composable
fun NasaImageGridScreen(
    modifier: Modifier = Modifier,
    viewModel: NasaImageGridScreenViewModel = hiltViewModel(),
) {
    val nasaImagePagingItems: LazyPagingItems<NasaImage> = viewModel.imageListState.collectAsLazyPagingItems()

    when (nasaImagePagingItems.loadState.refresh) {
        is LoadState.NotLoading -> {
            NasaImageGrid(
                images = nasaImagePagingItems,
                onShowBottomSheet = { title: String, url: String, description: String, photographer: String, location: String -> viewModel.showBottomSheet(title, url, description, photographer, location) },
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
        is LoadState.Loading -> {
            Text(text = "Fetching data. Please wait...")
        }

        else -> {
            Text(text = "An error has occurred. Please try again later.")
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NasaImageGrid(
    images: LazyPagingItems<NasaImage>,
    onShowBottomSheet: (String, String, String, String, String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxSize(),
    ) {
        items(images.itemCount) { index ->
            GlideImage(
                model = images[index]?.url,
                contentDescription = "Nasa image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable(onClick = {
                        images[index]?.let {
                            onShowBottomSheet(
                                it.title,
                                it.url,
                                it.description,
                                it.photographer,
                                it.location,
                            )
                        }
                    })
                    .size(100.dp)
            )
        }
    }
}