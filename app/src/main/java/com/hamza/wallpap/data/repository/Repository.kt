package com.hamza.wallpap.data.repository

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.*
import com.hamza.wallpap.data.local.UnsplashDatabase
import com.hamza.wallpap.data.paging.SearchPagingSource
import com.hamza.wallpap.data.paging.UnsplashRemoteMediator
import com.hamza.wallpap.data.remote.UnsplashApi
import com.hamza.wallpap.data.screens.home.HomeViewModel
import com.hamza.wallpap.data.screens.search.SearchChip
import com.hamza.wallpap.model.UnsplashImage
import com.hamza.wallpap.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashDatabase: UnsplashDatabase
) {
//    val homeViewModel: HomeViewModel by viewModels()
    val wallpaperItems =
        arrayListOf(
            SearchChip("Popular", "hd amoled wallpapers"),
            SearchChip("Night", "Night"),
            SearchChip("Mobile", "Android Wallpapers"),
            SearchChip("Dark", "Dark"),
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