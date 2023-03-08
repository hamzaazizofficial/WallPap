package com.hamza.wallpap.ui.screens.favourite

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.hamza.wallpap.R
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.model.FavouriteUrls
import com.hamza.wallpap.ui.theme.iconColor
import com.hamza.wallpap.ui.theme.maven_pro_regular
import com.hamza.wallpap.ui.theme.textColor
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouriteScreen(
    favUrlsViewModel: FavUrlsViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    lazyStaggeredGridState: LazyStaggeredGridState,
) {
    val data = favUrlsViewModel.getAllFavUrls.observeAsState(listOf())

    if (data.value.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Your Favourite Wallpapers will appear here",
                fontSize = 16.sp,
                fontFamily = maven_pro_regular,
                color = MaterialTheme.colors.textColor,
                textAlign = TextAlign.Center
            )
        }
    }

    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2), state = lazyStaggeredGridState) {
        items(data.value) { favUrl ->
            val height = remember {
                Random.nextInt(140, 380).dp
            }
            FavouriteItem(favUrl, favUrlsViewModel, navController, height)
        }
    }
}

@Composable
fun FavouriteItem(
    favUrl: FavouriteUrls,
    favUrlsViewModel: FavUrlsViewModel,
    navController: NavHostController,
    height: Dp,
) {
    val painter = rememberImagePainter(data = favUrl.full) {
        crossfade(durationMillis = 1000)
        error(R.drawable.ic_placeholder)
//        placeholder(R.drawable.ic_placeholder)
    }

    val fullEncodedUrl = URLEncoder.encode(favUrl.full, StandardCharsets.UTF_8.toString())

    Card(
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(2.dp),
        modifier = Modifier
            .padding(1.5.dp)
            .height(height)
            .clickable { navController.navigate("fav_wallpaper_screen/$fullEncodedUrl") },
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = "Unsplash Image",
                contentScale = ContentScale.Crop
            )

            Icon(
                imageVector = Icons.Default.Delete,
                tint = MaterialTheme.colors.iconColor,
                contentDescription = null,
                modifier = Modifier
                    .align(
                        Alignment.TopEnd
                    )
                    .padding(10.dp)
                    .clickable {
                        favUrlsViewModel.deleteFavouriteUrl(favUrl)
                    }
            )
        }
    }
}