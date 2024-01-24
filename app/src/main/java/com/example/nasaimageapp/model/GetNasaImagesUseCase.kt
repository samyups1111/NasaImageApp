package com.example.nasaimageapp.model

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.nasaimageapp.model.database.AppDatabase
import com.example.nasaimageapp.networking.NasaRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNasaImagesUseCase @Inject constructor(
    private val repository: NasaImageRepository,
    private val db: AppDatabase,
) {
    private val nasaImageDao = db.nasaImageDao()
    @OptIn(ExperimentalPagingApi::class)
    fun invoke(query: String): Flow<PagingData<NasaImage>> =
        Pager(
            config = PagingConfig(
                pageSize = 100,
                prefetchDistance = 2,
            ),
            remoteMediator = NasaRemoteMediator(query, db, repository)
        ) {
            nasaImageDao.getPagedList(query)
        }
            .flow
}