package com.example.nasaimageapp.view

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nasaimageapp.model.NasaImage

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NasaImageGridScreen(
    modifier: Modifier = Modifier,
    viewModel: NasaImageGridScreenViewModel = hiltViewModel(),
) {
    val nasaImagePagingItems: LazyPagingItems<NasaImage> = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    when (nasaImagePagingItems.loadState.refresh) {
        is LoadState.NotLoading -> {
            Column(
                modifier = modifier
                    .fillMaxSize(),
            ) {
                NasaImageGrid(
                    images = nasaImagePagingItems,
                    onShowBottomSheet = { title: String, url: String, description: String, photographer: String, location: String -> viewModel.showBottomSheet(title, url, description, photographer, location) },
                    modifier = Modifier
                        .weight(1F)
                )
                SearchBar(
                    query = viewModel.searchBarQuery,
                    active = true,
                    onActiveChange = { },
                    onQueryChange = { query: String -> viewModel.updateSearchBarQuery(query) },
                    onSearch = { query: String -> viewModel.search(query) },
                    placeholder = { Text(text = "search...") },
                    content = {},
                    modifier = Modifier
                        .height(75.dp)
                        .padding(3.dp)
                        .border(
                            width = 1.dp,
                            brush = Brush.horizontalGradient(listOf(Color.Black, Color.Black)),
                            shape = CircleShape,
                        )
                )
            }
            if (viewModel.nasaImageDetailsBottomSheetState is NasaImageDetailsBottomSheetState.SHOW) {
                val selectedImage = viewModel.nasaImageDetailsBottomSheetState as NasaImageDetailsBottomSheetState.SHOW
                NasaImageDetailsBottomSheet(
                    title = selectedImage.title,
                    url = selectedImage.url,
                    photographer = selectedImage.photographer,
                    location = selectedImage.location,
                    description = selectedImage.description,
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
        modifier = modifier,
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