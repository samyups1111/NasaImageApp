package com.example.nasaimageapp.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NasaImageRepository @Inject constructor(
    private val nasaImagePagingSource: NasaImagePagingSource,
) {

    fun getNasaImages(): Flow<PagingData<NasaImage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 100,
                prefetchDistance = 2,
            ),
            pagingSourceFactory = { nasaImagePagingSource }
        ).flow
    }
}