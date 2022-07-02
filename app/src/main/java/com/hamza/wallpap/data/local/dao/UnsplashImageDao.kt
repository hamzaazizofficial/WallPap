package com.hamza.wallpap.data.local.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.hamza.wallpap.model.FavouriteUrls
import com.hamza.wallpap.model.UnsplashImage

@Dao
interface UnsplashImageDao {

    @Query("SELECT * FROM unsplash_image_table")
    fun getAllImages(): PagingSource<Int, UnsplashImage>

    @Query("SELECT * FROM unsplash_fav_urls_table ORDER BY id ASC")
    fun getAllFavUrls(): LiveData<List<FavouriteUrls>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImages(images: List<UnsplashImage>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavourites(favouriteUrls: FavouriteUrls)

    @Delete
    suspend fun deleteFavouriteUrl(favouriteUrls: FavouriteUrls)

    @Query("DELETE FROM unsplash_fav_urls_table")
    suspend fun deleteAllFavUrls()

    @Query("DELETE FROM unsplash_image_table")
    suspend fun deleteAllImages()
}