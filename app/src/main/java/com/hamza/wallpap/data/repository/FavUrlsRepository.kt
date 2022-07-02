package com.hamza.wallpap.data.repository

import androidx.lifecycle.LiveData
import com.hamza.wallpap.data.local.dao.UnsplashImageDao
import com.hamza.wallpap.model.FavouriteUrls

class FavUrlsRepository(private val unsplashImageDao: UnsplashImageDao) {

    val readAllFavUrls: LiveData<List<FavouriteUrls>> = unsplashImageDao.getAllFavUrls()

    suspend fun addFavUrl(favUrls: FavouriteUrls){
        unsplashImageDao.addToFavourites(favUrls)
    }

    suspend fun deleteFavUrl(favUrls: FavouriteUrls){
        unsplashImageDao.deleteFavouriteUrl(favUrls)
    }

    suspend fun deleteAllFavUrls(){
        unsplashImageDao.deleteAllFavUrls()
    }
}