package com.hamza.wallpap.data.screens.firestore.amoled

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.hamza.wallpap.R
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AmoledScreen(
    navController: NavHostController,
    amoledViewModel: AmoledViewModel,
    scaffoldState: ScaffoldState
) {
    val data = amoledViewModel.wallpaperItems
    val scope = rememberCoroutineScope()

    BackHandler {
        if (scaffoldState.drawerState.isOpen) {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        } else {
            navController.navigate("home_screen")
        }
    }

    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        data.forEach { url->
            item()
            { AmoledItem(amoledUrl = url, navController) }
        }
    }
}

@Composable
fun AmoledItem(amoledUrl: String, navController: NavHostController) {
    val painter = rememberImagePainter(data = amoledUrl) {
        crossfade(durationMillis = 1000)
        error(R.drawable.ic_placeholder)
//        placeholder(R.drawable.ic_placeholder)
    }

    val fullEncodedUrl = URLEncoder.encode(amoledUrl, StandardCharsets.UTF_8.toString())

    Card(
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(2.dp),
        modifier = Modifier
            .padding(1.5.dp)
            .height(300.dp)
            .clickable { navController.navigate("amoled_full_screen/$fullEncodedUrl") },
    ) {
        Box(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {

            LinearProgressIndicator(
                modifier = Modifier.align(Alignment.BottomCenter),
                color = MaterialTheme.colors.secondary
            )

            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = "Firestore Image",
                contentScale = ContentScale.Crop
            )
        }
    }
}
