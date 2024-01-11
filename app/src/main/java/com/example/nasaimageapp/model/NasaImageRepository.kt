package com.example.nasaimageapp.model

import com.example.nasaimageapp.networking.NasaImageService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NasaImageRepository @Inject constructor(
    private val nasaImageService: NasaImageService,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend fun getImagesFromNetwork(): List<NasaImage> = withContext(coroutineDispatcher) {

        val response = nasaImageService.getNasaImages()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            val items = body.collection.items
            items.toNasaImageDataList()
        } else {
            emptyList()
        }
    }
}