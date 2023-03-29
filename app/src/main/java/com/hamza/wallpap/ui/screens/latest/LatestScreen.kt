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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hamza.wallpap.ui.theme.maven_pro_regular
import com.hamza.wallpap.ui.theme.systemBarColor
import com.hamza.wallpap.ui.theme.textColor
import com.hamza.wallpap.util.isOnline
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.M)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LatestScreen(
    navController: NavHostController,
    amoledViewModel: LatestViewModel,
    scaffoldState: ScaffoldState,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)
    val data = amoledViewModel.wallpaperItems
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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
                tint = Color.White,
                imageVector = Icons.Default.NetworkCheck, contentDescription = null,
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = "Check your Network Connection.",
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
        LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
            data.forEach { url ->
                item()
                {
                    val height = remember {
                        Random.nextInt(140, 380).dp
                    }
                    LatestItem(amoledUrl = url, navController, height, context)
                }
            }
        }
    }
}

@Composable
fun LatestItem(amoledUrl: String, navController: NavHostController, height: Dp, context: Context) {
    val fullEncodedUrl = URLEncoder.encode(amoledUrl, StandardCharsets.UTF_8.toString())

    Card(
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(2.dp),
        modifier = Modifier
            .padding(2.5.dp)
            .height(height)
            .clickable { navController.navigate("amoled_full_screen/$fullEncodedUrl") },
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
                    .data(amoledUrl)
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
