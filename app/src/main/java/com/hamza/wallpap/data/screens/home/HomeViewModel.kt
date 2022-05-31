package com.hamza.wallpap.data.screens.home

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.hamza.wallpap.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {

    val query = MutableStateFlow<String?>(null)

    var unsplashImageUri by mutableStateOf<Uri?>(null)
    var unsplashBitmap by mutableStateOf<Bitmap?>(null)

    val itemsFlow = query.flatMapLatest {
        if (it.isNullOrEmpty()) {
            repository.getAllImages()
        } else {
            repository.searchImages(it)
        }
    }
}
