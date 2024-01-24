package com.example.nasaimageapp.networking

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.nasaimageapp.model.NasaImage
import com.example.nasaimageapp.model.NasaImageRepository
import com.example.nasaimageapp.model.database.AppDatabase
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NasaRemoteMediator(
    private val query: String,
    private val db: AppDatabase,
    private val repository: NasaImageRepository,
) : RemoteMediator<Int, NasaImage>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NasaImage>
    ): MediatorResult {

        val nasaDao = db.nasaImageDao()
        val remoteKeyDao = db.remoteKeyDao()

        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = remoteKeyDao.remoteKeyByQuery(query)

                    if (remoteKey.nextPage == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.nextPage
                }
            }
            val response = repository.getNasaImages(query, page)

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.deleteByQuery(query)
                    nasaDao.deleteByQuery(query)
                }
                nasaDao.insertAll(response)
            }
            val nextPage = page + 1
            remoteKeyDao.insertOrReplace(
                RemoteKey(query, nextPage)
            )
            nasaDao.insertAll(response)

            MediatorResult.Success(
                endOfPaginationReached = response.isEmpty()
            )
        }
        catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}