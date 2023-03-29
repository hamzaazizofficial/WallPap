package com.hamza.wallpap.data.repository

import androidx.compose.runtime.mutableStateOf
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hamza.wallpap.data.local.UnsplashDatabase
import com.hamza.wallpap.data.remote.UnsplashApi
import com.hamza.wallpap.model.UnsplashImage
import com.hamza.wallpap.paging.SearchPagingSource
import com.hamza.wallpap.paging.UnsplashRemoteMediator
import com.hamza.wallpap.ui.screens.search.SearchChip
import com.hamza.wallpap.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashDatabase: UnsplashDatabase,
) {
    val wallpaperItems =
        arrayListOf(
            SearchChip("Digital Art", "digital art"),
            SearchChip("Popular", "4k wallpapers"),
            SearchChip("Space", "space"),
            SearchChip("Dark", "dark wallpapers"),
            SearchChip("Night", "night wallpapers"),
            SearchChip("Mobile", "Android Wallpapers"),
            SearchChip("Nature", "Nature"),
        )

    var selectedIndex = mutableStateOf(0)

    fun getAllImages(): Flow<PagingData<UnsplashImage>> {
        val pagingSourceFactory = { unsplashDatabase.unsplashImageDao().getAllImages() }
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(
                unsplashApi = unsplashApi,
                unsplashDatabase = unsplashDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    fun searchImages(query: String): Flow<PagingData<UnsplashImage>> {
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(
                unsplashApi = unsplashApi,
                unsplashDatabase = unsplashDatabase
            ),
            pagingSourceFactory = {
                SearchPagingSource(unsplashApi = unsplashApi, query = query)
            }
        ).flow
    }

}