package com.hamza.wallpap.ui.screens.latest

import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.hamza.wallpap.R
import com.hamza.wallpap.ui.theme.maven_pro_regular
import com.hamza.wallpap.ui.theme.systemBarColor
import com.hamza.wallpap.ui.theme.textColor
import com.hamza.wallpap.ui.theme.topAppBarTitle
import com.hamza.wallpap.util.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@RequiresApi(Build.VERSION_CODES.M)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LatestScreen(
    navController: NavHostController,
    latestViewModel: LatestViewModel,
    scaffoldState: ScaffoldState,
    systemUiController: SystemUiController,
    items: List<WallpaperItem>,
    context: Context,
    scope: CoroutineScope,
) {
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)
    var isRefreshing by remember { mutableStateOf(false) }

    val refreshAction: () -> Unit = {
        isRefreshing = true
        latestViewModel.refreshWallpaperItems().apply {
            isRefreshing = false
        }
    }

    BackHandler {
        if (scaffoldState.drawerState.isOpen) {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        } else {
            navController.navigate("home_screen")
        }
    }

    if (!isOnline(context)) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background)
        ) {
            Icon(
                tint = MaterialTheme.colors.topAppBarTitle,
                imageVector = Icons.Default.NetworkCheck, contentDescription = null,
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = stringResource(id = R.string.check_network) + "\n" + stringResource(id = R.string.reopen_app),
                color = MaterialTheme.colors.textColor,
                fontFamily = maven_pro_regular,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
            onRefresh = refreshAction
        ) {
            LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
                items.forEach { latestItem ->
                    item()
                    {
                        LatestItem(latestItem = latestItem, navController, context)
                    }
                }
            }
        }
    }
}

@Composable
fun LatestItem(
    latestItem: WallpaperItem,
    navController: NavHostController,
    context: Context,
) {
    val fullEncodedUrl = URLEncoder.encode(latestItem.imageUrl, StandardCharsets.UTF_8.toString())

    Card(
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(2.dp),
        modifier = Modifier
            .padding(2.5.dp)
            .height(latestItem.height.dp)
            .clickable { navController.navigate("latest_full_screen/$fullEncodedUrl") },
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
                    .data(latestItem.imageUrl)
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
        }
    }
}
