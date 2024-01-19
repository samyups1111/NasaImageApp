package com.example.nasaimageapp.model

import javax.inject.Inject

class GetNasaImagesUseCase @Inject constructor(
    private val repository: NasaImageRepository
) {
    suspend fun invoke(query: String, page: Int): List<NasaImage> {
        return repository.getNasaImages(query, page)
    }
}