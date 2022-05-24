package com.hamza.wallpap.data.screens.wallpaper

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun WallpaperFullScreen(regularUrl: String, fullUrl: String) {
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        var data by remember { mutableStateOf(regularUrl) }

        val painter = rememberImagePainter(data = data) {
            crossfade(durationMillis = 1)
//            error(R.drawable.ic_placeholder)
//            placeholder(R.drawable.loading)
        }

        LinearProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter))

        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "Unsplash Image",
            contentScale = ContentScale.Crop
        )

//                Surface(
//            modifier = Modifier
//                .height(60.dp)
//                .fillMaxWidth()
//                .alpha(ContentAlpha.medium)
//                .align(Alignment.TopEnd),
//            color = Color.Black
//        ) {}

        Icon(
            imageVector = Icons.Default.HighQuality, contentDescription = null, modifier = Modifier
                .align(
                    Alignment.TopEnd
                )
                .padding(18.dp)
                .clickable {
                    data = fullUrl
                },
            tint = Color.White
        )
    }

}