package com.hamza.wallpap.data.screens.wallpaper

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.HighQuality
import androidx.compose.material.icons.rounded.Fullscreen
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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

        var scale by remember { mutableStateOf(ContentScale.Crop) }
        var showFitScreenBtn by remember { mutableStateOf(true) }
        var showCropScreenBtn by remember { mutableStateOf(false) }

        if (showFitScreenBtn) {
            LinearProgressIndicator(modifier = Modifier.align(Alignment.BottomCenter))
        }

        Image(
            contentScale = scale,
            modifier = Modifier.fillMaxSize(),
            painter = painter,
            contentDescription = "Unsplash Image",
        )

        Surface(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .alpha(ContentAlpha.disabled)
                .align(Alignment.TopEnd),
            color = Color.Black
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp),
                horizontalArrangement = Arrangement.End
            ) {

                if (showFitScreenBtn) {
                    Icon(
                        imageVector = Icons.Default.Fullscreen,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                scale = ContentScale.Fit
                                showFitScreenBtn = false
                                showCropScreenBtn = true
                            },
                        tint = Color.White
                    )
                }

                if (showCropScreenBtn) {
                    Icon(
                        imageVector = Icons.Rounded.Fullscreen,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                scale = ContentScale.Crop
                                showCropScreenBtn = false
                                showFitScreenBtn = true
                            },
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.padding(end = 10.dp))

                Icon(
                    imageVector = Icons.Default.HighQuality,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {
                            data = fullUrl
                        },
                    tint = Color.White
                )
            }
        }
    }
}