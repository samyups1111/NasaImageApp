package com.example.nasaimageapp.model

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nasaimageapp.networking.NasaImageService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class NasaImagePagingSource @Inject constructor(
    private val nasaImageService: NasaImageService,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
    ) : PagingSource<Int, NasaImage>() {

    override fun getRefreshKey(state: PagingState<Int, NasaImage>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NasaImage> = withContext(coroutineDispatcher) {
        try {
            val pageNumber = params.key ?: 0

            val response = nasaImageService.getNasaImages()
            val body = response.body()
            val items = body?.collection?.items

            val prevKey = if (pageNumber > 0) pageNumber -1 else null

            val nextKey = if (!items.isNullOrEmpty()) pageNumber + 1 else null

            LoadResult.Page(
                data = items!!.toNasaImageDataList(),
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}