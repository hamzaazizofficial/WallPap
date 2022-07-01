package com.hamza.wallpap.data.screens.favourite

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.hamza.wallpap.R
import com.hamza.wallpap.data.local.dao.FavUrlsViewModel
import com.hamza.wallpap.model.FavouriteUrls

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavouriteScreen(
    favUrlsViewModel: FavUrlsViewModel,
    navController: NavHostController, scaffoldState: ScaffoldState
) {
    val data = favUrlsViewModel.getAllFavUrls.observeAsState(listOf())

    LazyVerticalGrid(cells = GridCells.Fixed(2)) {
        items(data.value) { favUrl ->
            FavouriteItem(favUrl)
        }
    }
}

@Composable
fun FavouriteItem(favUrl: FavouriteUrls) {
    val painter = rememberImagePainter(data = favUrl.full) {
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
//            .clickable { navController.navigate("wallpaper_screen/$regularEncodedUrl/$fullEncodedUrl") },
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

//            if (homeViewModel.showUserDetails) {
//
//                Surface(
//                    modifier = Modifier
//                        .height(40.dp)
//                        .fillMaxWidth()
//                        .clickable {
//                            val browserIntent = Intent(
//                                Intent.ACTION_VIEW,
//                                Uri.parse("https://unsplash.com/@${unsplashImage.user.username}?utm_source=DemoApp&utm_medium=referral")
//                            )
//                            ContextCompat.startActivity(context, browserIntent, null)
//                        }
//                        .alpha(ContentAlpha.medium),
//                    color = Color.Black
//                ) {}
//                Row(
//                    modifier = Modifier
//                        .height(40.dp)
//                        .fillMaxWidth()
//                        .padding(horizontal = 6.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = buildAnnotatedString {
//                            append("Photo by ")
//                            withStyle(style = SpanStyle(fontWeight = FontWeight.Black)) {
//                                append(unsplashImage.user.username)
//                            }
//                            append(" on Unsplash")
//                        },
//                        color = Color.White,
//                        fontSize = MaterialTheme.typography.caption.fontSize,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//            }
        }
    }

}
