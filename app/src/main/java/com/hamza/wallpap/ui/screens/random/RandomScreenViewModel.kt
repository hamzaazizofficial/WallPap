package com.hamza.wallpap.ui.screens.random

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import com.hamza.wallpap.data.repository.Repository
import com.hamza.wallpap.model.UnsplashImage
import com.hamza.wallpap.ui.UnsplashImageUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.random.Random

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class RandomScreenViewModel @Inject constructor(
    repository: Repository,
) : ViewModel() {
    val itemsFlow = repository.getAllImages().map { pagingData ->
        pagingData.map { image: UnsplashImage ->
            UnsplashImageUI(image = image, height = Random.nextInt(140, 380))
        }
    }
}