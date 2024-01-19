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

    suspend fun getNasaImages(query: String, page: Int): List<NasaImage> = withContext(coroutineDispatcher) {
        val response = nasaImageService.getNasaImages(title = query, page = page)
        val body = response.body()
        val items = body?.collection?.items // todo: handle errors

        val nasaImages = mutableListOf<NasaImage>()

        items?.forEach { nasaItem ->
            nasaItem.toNasaImageData().let { nasaImage ->
                if (nasaImage != null) {
                    nasaImages.add(nasaImage)
                }
            }
        }
        nasaImages
    }
}