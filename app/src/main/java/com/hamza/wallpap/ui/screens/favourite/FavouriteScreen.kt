package com.hamza.wallpap.ui.screens.favourite

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
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
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.SystemUiController
import com.hamza.wallpap.R
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.model.FavouriteUrls
import com.hamza.wallpap.ui.theme.*
import com.hamza.wallpap.util.isOnline
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@RequiresApi(Build.VERSION_CODES.M)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FavouriteScreen(
    favUrlsViewModel: FavUrlsViewModel,
    navController: NavHostController,
    context: Context,
    favouriteItemsData: State<List<FavouriteUrls>>,
    systemUiController: SystemUiController,
) {
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)
    val configuration = LocalConfiguration.current
    val orientation = configuration.orientation

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
                imageVector = Icons.Default.NetworkCheck,
                contentDescription = null,
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = if (favouriteItemsData.value.isEmpty()) Alignment.Center else Alignment.TopCenter
        ) {
            AnimatedVisibility(
                visible = favouriteItemsData.value.isEmpty(),
                enter = scaleIn() + fadeIn(),
                exit = scaleOut() + fadeOut()
            ) {
                Text(
                    text = stringResource(id = R.string.your_fav_wallpapers),
                    fontSize = 18.sp,
                    fontFamily = maven_pro_regular,
                    color = MaterialTheme.colors.textColor,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(if (orientation == Configuration.ORIENTATION_LANDSCAPE) 3 else 2),
                content = {
                    items(favouriteItemsData.value) { favUrl ->
                        FavouriteItem(favUrl, favUrlsViewModel, navController, context)
                    }
                })
        }
    }
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
            .clickable { navController.navigate("fav_full_screen/$fullEncodedUrl/$regularEncodedUrl") },
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context).data(favUrl.regular).crossfade(1000).build(),
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
