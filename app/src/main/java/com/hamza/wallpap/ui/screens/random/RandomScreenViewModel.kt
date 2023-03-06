package com.hamza.wallpap.ui.screens.random

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.hamza.wallpap.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class RandomScreenViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {
    var showUserDetails by mutableStateOf(false)
    val itemsFlow = repository.getAllImages()
}