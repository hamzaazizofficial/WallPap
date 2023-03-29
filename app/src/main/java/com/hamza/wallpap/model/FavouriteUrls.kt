package com.hamza.wallpap.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hamza.wallpap.util.Constants
import kotlinx.serialization.Serializable

@Entity(tableName = Constants.UNSPLASH_FAV_URLS_TABLE)
@Serializable
data class FavouriteUrls(
    val id: Int,
    @PrimaryKey
    val full: String,
    val regular: String
)