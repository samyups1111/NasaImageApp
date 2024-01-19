package com.example.nasaimageapp.model

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException

class NasaImagePagingSource(
    private val query: String,
    private val getNasaImagesUseCase: GetNasaImagesUseCase,
    ) : PagingSource<Int, NasaImage>() {

    override fun getRefreshKey(state: PagingState<Int, NasaImage>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NasaImage> {

        val pageNumber = params.key ?: 1
        val items = getNasaImagesUseCase.invoke(query, pageNumber)

        val prevKey = if (pageNumber > 0) pageNumber -1 else null

        val nextKey = if (items.isNotEmpty()) pageNumber + 1 else null

        return try {
            LoadResult.Page(
                data = items,
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