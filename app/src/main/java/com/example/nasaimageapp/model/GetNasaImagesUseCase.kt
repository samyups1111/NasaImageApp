package com.example.nasaimageapp.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNasaImagesUseCase @Inject constructor(
    private val repository: NasaImageRepository
) {

    fun invoke(query: String): Flow<PagingData<NasaImage>> {
        return repository.getNasaImages(query)
    }
}