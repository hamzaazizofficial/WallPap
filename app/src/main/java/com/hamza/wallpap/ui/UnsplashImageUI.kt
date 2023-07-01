package com.hamza.wallpap.ui

import com.hamza.wallpap.model.UnsplashImage
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashImageUI(
    val image: UnsplashImage,
    val height: Int
)
