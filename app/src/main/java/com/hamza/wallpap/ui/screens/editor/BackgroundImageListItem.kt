package com.hamza.wallpap.ui.screens.editor

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.hamza.wallpap.R
import com.hamza.wallpap.ui.UnsplashImageUI
import com.hamza.wallpap.ui.theme.topAppBarTitle

@Composable
fun BackgroundImageListItem(
    unsplashImage: UnsplashImageUI,
    customWallpaperViewModel: CustomWallpaperViewModel,
    context: Context,
) {
    Card(
        backgroundColor = Color.Black,
        modifier = Modifier
            .padding(2.dp)
            .height(120.dp)
            .width(80.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(1.dp, MaterialTheme.colors.topAppBarTitle, RoundedCornerShape(4.dp)),
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .width(80.dp)
                .clickable {
                    customWallpaperViewModel.bgImageFullUrl.value = unsplashImage.image.urls.full
                    customWallpaperViewModel.bgImageRegularUrl.value = unsplashImage.image.urls.regular
                    customWallpaperViewModel.selectBgImageState.value = true
                    customWallpaperViewModel.bgImageUri.value = null
                    customWallpaperViewModel.imageRotate.value = 0f
                    customWallpaperViewModel.contentScale.value = ContentScale.Crop
                },
            contentAlignment = Alignment.BottomCenter
        ) {

            SubcomposeAsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data(unsplashImage.image.urls.regular)
                    .crossfade(1000)
                    .error(R.drawable.ic_placeholder)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    LinearProgressIndicator(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        color = MaterialTheme.colors.secondary
                    )
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        }
    }
}