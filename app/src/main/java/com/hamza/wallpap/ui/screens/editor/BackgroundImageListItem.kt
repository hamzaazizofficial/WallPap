package com.hamza.wallpap.ui.screens.editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.hamza.wallpap.R
import com.hamza.wallpap.model.UnsplashImage

@Composable
fun BackgroundImageListItem(
    unsplashImage: UnsplashImage,
    customWallpaperViewModel: CustomWallpaperViewModel,
) {

    val painter = rememberImagePainter(data = unsplashImage.urls.regular) {
        crossfade(durationMillis = 1000)
        error(R.drawable.ic_placeholder)
    }

    Card(
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(2.dp)
            .height(120.dp)
            .width(80.dp),
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .width(80.dp)
                .clickable {
                    customWallpaperViewModel.boxColor.value = Color(0xF1FFFFFF)
                    customWallpaperViewModel.bgImageUrl.value = unsplashImage.urls.regular
                },
            contentAlignment = Alignment.BottomCenter
        ) {

            Image(
                painter = painter,
                contentDescription = "Unsplash Image",
                contentScale = ContentScale.Crop
            )
        }
    }
}