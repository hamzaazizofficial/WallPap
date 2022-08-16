package com.hamza.wallpap.data.screens.splash

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hamza.wallpap.R
import com.hamza.wallpap.data.navigation.Screen
import com.hamza.wallpap.ui.theme.maven_pro_regular
import com.hamza.wallpap.ui.theme.splashBackgroundColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
//    val infiniteTransition = rememberInfiniteTransition()
//    val angle by infiniteTransition.animateFloat(
//        initialValue = 0F,
//        targetValue = 360F,
//        animationSpec = infiniteRepeatable(
//            animation = tween(1000, easing = {
//                OvershootInterpolator().getInterpolation(it)
//            }
//            ),
//            repeatMode = RepeatMode.Reverse
//        )
//    )
//    var startAnimation by remember { mutableStateOf(false) }
//    val alphaAnim = animateFloatAsState(
//        targetValue = if (startAnimation) 1f else 0f,
//        animationSpec = tween(
//            durationMillis = 2000
//        )
//    )

    LaunchedEffect(key1 = true) {
//        startAnimation = true
        delay(2000)
        navController.popBackStack()
        navController.navigate(Screen.Home.route)
    }
    Splash()
}

@Composable
fun Splash() {
    val infiniteTransition = rememberInfiniteTransition()
    val color by infiniteTransition.animateColor(
        initialValue = MaterialTheme.colors.splashBackgroundColor,
        targetValue = Color.White,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .background(color)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Image(
                painterResource(id = R.drawable.letter_w),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(top = 35.dp, bottom = 0.dp)
                    .size(110.dp)
            )

            Text(
                buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFF243447),
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = maven_pro_regular
                        )
                    ) {
                        append("Wall")
                    }

                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFFF5252),
                            fontSize = 34.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = maven_pro_regular
                        )
                    ) {
                        append("Pap")
                    }
                })
        }
    }
}
