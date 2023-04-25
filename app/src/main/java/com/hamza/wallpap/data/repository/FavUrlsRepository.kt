package com.hamza.wallpap.data.repository

import androidx.lifecycle.LiveData
import com.hamza.wallpap.data.local.dao.UnsplashImageDao
import com.hamza.wallpap.model.FavouriteUrls

class FavUrlsRepository(private val unsplashImageDao: UnsplashImageDao) {

    val readAllFavUrls: LiveData<List<FavouriteUrls>> = unsplashImageDao.getAllFavUrls()

    suspend fun addFavUrl(favUrls: FavouriteUrls){
        val existingUrl = unsplashImageDao.getFavouriteUrl(favUrls.id)
        if (existingUrl == null) {
            unsplashImageDao.addToFavourites(favUrls)
        }
//        unsplashImageDao.addToFavourites(favUrls)
    }

    suspend fun getFavouriteUrlById(id: Int): FavouriteUrls? {
        return unsplashImageDao.getFavouriteUrl(id)
    }

//    suspend fun getFavUrl(favouriteUrls: FavouriteUrls?) {
//        if (favouriteUrls != null) {
//            unsplashImageDao.getFavouriteUrl(favouriteUrls.id)
//        }
//    }

    suspend fun deleteFavUrl(favUrls: FavouriteUrls){
        unsplashImageDao.deleteFavouriteUrl(favUrls)
    }

    suspend fun deleteAllFavUrls(){
        unsplashImageDao.deleteAllFavUrls()
    }
}