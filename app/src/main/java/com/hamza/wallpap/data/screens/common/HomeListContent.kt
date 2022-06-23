package com.hamza.wallpap.data.screens.common

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.paging.ExperimentalPagingApi
import androidx.paging.compose.LazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.hamza.wallpap.R
import com.hamza.wallpap.data.screens.home.HomeViewModel
import com.hamza.wallpap.model.UnsplashImage
import com.hamza.wallpap.ui.theme.HeartRed
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagingApi::class)
@ExperimentalCoilApi
@Composable
fun HomeListContent(
    items: LazyPagingItems<UnsplashImage>,
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {
    Log.d("Error", items.loadState.toString())

    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(items.itemCount) {
            items[it]?.let { unsplashImage ->
                UnsplashItem(
                    unsplashImage = unsplashImage,
                    navController,
                    homeViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalPagingApi::class)
@ExperimentalCoilApi
@Composable
fun UnsplashItem(
    unsplashImage: UnsplashImage,
    navController: NavHostController,
    homeViewModel: HomeViewModel
) {

    val regularUrl = unsplashImage.urls.regular
    val fullUrl = unsplashImage.urls.full
    val regularEncodedUrl = URLEncoder.encode(regularUrl, StandardCharsets.UTF_8.toString())
    val fullEncodedUrl = URLEncoder.encode(fullUrl, StandardCharsets.UTF_8.toString())
    val context = LocalContext.current


    val painter = rememberImagePainter(data = unsplashImage.urls.regular) {
        crossfade(durationMillis = 1000)
        error(R.drawable.ic_placeholder)
        placeholder(R.drawable.ic_placeholder)
    }

    Card(
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(3.dp)
            .height(300.dp)
            .clickable { navController.navigate("wallpaper_screen/$regularEncodedUrl/$fullEncodedUrl") },
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

            if (homeViewModel.showUserDetails) {

                Surface(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .clickable {
                            val browserIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://unsplash.com/@${unsplashImage.user.username}?utm_source=DemoApp&utm_medium=referral")
                            )
                            startActivity(context, browserIntent, null)
                        }
                        .alpha(ContentAlpha.medium),
                    color = Color.Black
                ) {}
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append("Photo by ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append(unsplashImage.user.username)
                            }
                            append(" on Unsplash")
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }


}

@Composable
fun LikeCounter(
    modifier: Modifier,
    painter: Painter,
    likes: String
) {
    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            painter = painter,
            contentDescription = "Heart Icon",
            tint = HeartRed
        )
        Divider(modifier = Modifier.width(6.dp))
        Text(
            text = likes,
            color = Color.White,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
