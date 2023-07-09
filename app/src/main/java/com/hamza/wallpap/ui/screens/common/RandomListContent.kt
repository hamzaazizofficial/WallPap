package com.hamza.wallpap.ui.screens.common

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
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
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.hamza.wallpap.R
import com.hamza.wallpap.ui.UnsplashImageUI
import com.hamza.wallpap.ui.screens.home.HomeViewModel
import com.hamza.wallpap.ui.screens.random.RandomScreenViewModel
import com.hamza.wallpap.ui.theme.maven_pro_regular
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalFoundationApi::class, ExperimentalCoilApi::class, ExperimentalPagingApi::class)
@Composable
fun RandomListContent(
    items: LazyPagingItems<UnsplashImageUI>,
    navController: NavHostController,
    randomViewModel: RandomScreenViewModel,
    homeViewModel: HomeViewModel,
    refreshState: SwipeRefreshState,
    onRefresh: () -> Unit,
) {
    Log.d("Error", items.loadState.toString())
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

//    SwipeRefresh(
//        state = refreshState,
//        onRefresh = onRefresh
//    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(if (orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2),
            state = rememberLazyStaggeredGridState(),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(2.dp)
        ) {
            items(items.itemCount) {
                items[it]?.let { unsplashImage ->
                    RandomUnsplashItem(
                        unsplashImage = unsplashImage,
                        navController,
                        randomViewModel,
                        homeViewModel
                    )
                }
            }
        }
//    }
}

@OptIn(ExperimentalPagingApi::class, ExperimentalAnimationApi::class)
@ExperimentalCoilApi
@Composable
fun RandomUnsplashItem(
    unsplashImage: UnsplashImageUI,
    navController: NavHostController,
    randomViewModel: RandomScreenViewModel,
    homeViewModel: HomeViewModel,
) {

    val regularUrl = unsplashImage.image.urls.regular
    val fullUrl = unsplashImage.image.urls.full
    val regularEncodedUrl = URLEncoder.encode(regularUrl, StandardCharsets.UTF_8.toString())
    val fullEncodedUrl = URLEncoder.encode(fullUrl, StandardCharsets.UTF_8.toString())
    val context = LocalContext.current

    Card(
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(2.dp),
        modifier = Modifier
            .padding(2.5.dp)
            .height(unsplashImage.height.dp)
            .clickable {
                navController.navigate("home_wallpaper_screen/$regularEncodedUrl/$fullEncodedUrl")
            },
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
                    .data(unsplashImage.image.urls.regular)
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

            AnimatedVisibility(
                visible = homeViewModel.showUserDetails,
                enter = slideInHorizontally() + fadeIn(),
                exit = slideOutHorizontally() + fadeOut(),
            ) {
                Surface(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                        .clickable {
                            val browserIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://unsplash.com/@${unsplashImage.image.user.username}?utm_source=DemoApp&utm_medium=referral")
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
                            append(stringResource(id = R.string.photo_by) + " ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
                                append(unsplashImage.image.user.username)
                            }
                            append(" " + stringResource(id = R.string.on_unsplash))
                        },
                        color = Color.White,
                        fontSize = MaterialTheme.typography.caption.fontSize,
                        fontFamily = maven_pro_regular,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}
