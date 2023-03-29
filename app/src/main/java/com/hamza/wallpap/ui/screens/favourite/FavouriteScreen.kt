package com.hamza.wallpap.ui.screens.favourite

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.model.FavouriteUrls
import com.hamza.wallpap.ui.theme.bottomAppBarContentColor
import com.hamza.wallpap.ui.theme.maven_pro_regular
import com.hamza.wallpap.ui.theme.systemBarColor
import com.hamza.wallpap.ui.theme.textColor
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun FavouriteScreen(
    favUrlsViewModel: FavUrlsViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    context: Context,
    favouriteItemsData: State<List<FavouriteUrls>>,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)

    if (favouriteItemsData.value.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Your Favourite Wallpapers will appear here",
                fontSize = 18.sp,
                fontFamily = maven_pro_regular,
                color = MaterialTheme.colors.textColor,
                textAlign = TextAlign.Center
            )
        }
    }

    LazyVerticalGrid(columns = GridCells.Fixed(2), content = {
        items(favouriteItemsData.value) { favUrl ->
            FavouriteItem(favUrl, favUrlsViewModel, navController, context)
        }
    })
}

@Composable
fun FavouriteItem(
    favUrl: FavouriteUrls,
    favUrlsViewModel: FavUrlsViewModel,
    navController: NavHostController,
    context: Context,
) {
    val fullEncodedUrl = URLEncoder.encode(favUrl.full, StandardCharsets.UTF_8.toString())
    val regularEncodedUrl = URLEncoder.encode(favUrl.regular, StandardCharsets.UTF_8.toString())

    Card(
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(2.dp),
        modifier = Modifier
            .padding(2.5.dp)
            .height(280.dp)
            .clickable { navController.navigate("fav_wallpaper_screen/$fullEncodedUrl/$regularEncodedUrl") },
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest
                    .Builder(context)
                    .data(favUrl.regular)
                    .crossfade(1000)
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
                    SubcomposeAsyncImageContent(modifier = Modifier.fillMaxSize())
                }
            }

            IconButton(
                onClick = { favUrlsViewModel.deleteFavouriteUrl(favUrl) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    tint = MaterialTheme.colors.bottomAppBarContentColor,
                    contentDescription = null
                )
            }
        }
    }
}
