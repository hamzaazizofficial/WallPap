package com.hamza.wallpap.ui.screens.editor

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.hamza.wallpap.R
import com.hamza.wallpap.model.UnsplashImage

@Composable
fun BackgroundImageListItem(
    unsplashImage: UnsplashImage,
    customWallpaperViewModel: CustomWallpaperViewModel,
) {

    val painter = rememberImagePainter(data = unsplashImage.urls.regular) {
        crossfade(durationMillis = 1000)
        error(R.drawable.ic_placeholder)
    }

    Card(
        backgroundColor = Color.Black,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier
            .padding(2.dp)
            .height(120.dp)
            .width(80.dp),
    ) {
        Box(
            modifier = Modifier
                .height(120.dp)
                .width(80.dp)
                .clickable {
                    customWallpaperViewModel.boxColor.value = Color(0xF1FFFFFF)
                    customWallpaperViewModel.bgImageUrl.value = unsplashImage.urls.regular
                },
//                .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Image(
//                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = "Unsplash Image",
                contentScale = ContentScale.Crop
            )

//            AnimatedVisibility(
//                visible = hotViewModel.showUserDetails,
//                enter = slideInHorizontally() + fadeIn(),
//                exit = slideOutHorizontally() + fadeOut(),
//            ) {
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
//                        fontFamily = maven_pro_regular,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//            }
        }
    }
}