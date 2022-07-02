package com.hamza.wallpap.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hamza.wallpap.data.local.dao.UnsplashImageDao
import com.hamza.wallpap.data.local.dao.UnsplashRemoteKeysDao
import com.hamza.wallpap.model.FavouriteUrls
import com.hamza.wallpap.model.UnsplashImage
import com.hamza.wallpap.model.UnsplashRemoteKeys
import com.hamza.wallpap.util.Constants

@Database(
    entities = [UnsplashImage::class, FavouriteUrls::class, UnsplashRemoteKeys::class],
    version = 1
)
abstract class UnsplashDatabase : RoomDatabase() {

    abstract fun unsplashImageDao(): UnsplashImageDao
    abstract fun unsplashRemoteKeysDao(): UnsplashRemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: UnsplashDatabase? = null

        fun getDatabase(context: Context): UnsplashDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UnsplashDatabase::class.java,
                        Constants.UNSPLASH_DATABASE
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }

}
