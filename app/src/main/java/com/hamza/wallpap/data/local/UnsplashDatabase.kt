package com.hamza.wallpap.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hamza.wallpap.data.local.dao.UnsplashImageDao
import com.hamza.wallpap.data.local.dao.UnsplashRemoteKeysDao
import com.hamza.wallpap.model.UnsplashImage
import com.hamza.wallpap.model.UnsplashRemoteKeys

@Database(entities = [UnsplashImage::class, UnsplashRemoteKeys::class], version = 1)
abstract class UnsplashDatabase: RoomDatabase() {

    abstract fun unsplashImageDao(): UnsplashImageDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao

}