package com.hamza.wallpap.data.local.dao

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hamza.wallpap.data.local.UnsplashDatabase
import com.hamza.wallpap.data.repository.FavUrlsRepository
import com.hamza.wallpap.model.FavouriteUrls
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavUrlsViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val readAllFavUrls: LiveData<List<FavouriteUrls>>
    private val unsplashImageDao = UnsplashDatabase.getDatabase(application).unsplashImageDao()
    private val repository = FavUrlsRepository(unsplashImageDao)
    val getAllFavUrls = repository.readAllFavUrls

    init {
        readAllFavUrls = repository.readAllFavUrls
    }


    fun addToFav(favouriteUrls: FavouriteUrls) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addFavUrl(favouriteUrls)
        }
    }
}