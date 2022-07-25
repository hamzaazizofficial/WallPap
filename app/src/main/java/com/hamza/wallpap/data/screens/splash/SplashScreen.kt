package com.hamza.wallpap.data.screens.splash

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.hamza.wallpap.R
import com.hamza.wallpap.data.navigation.Screen
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
    var startAnimation by remember { mutableStateOf(false) }
//    val alphaAnim = animateFloatAsState(
//        targetValue = if (startAnimation) 1f else 0f,
//        animationSpec = tween(
//            durationMillis = 2000
//        )
//    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
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
        initialValue = MaterialTheme.colors.secondary,
        targetValue = Color.White,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .background(Color.Black)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painterResource(R.drawable.app_logo),
            contentDescription = null,
//            contentScale = Con,
//            colorFilter = ColorFilter.tint(
////                if (!isSystemInDarkTheme()) onPrimary.copy(0.8f) else onPrimaryDark.copy(0.8f)
//            ),
            modifier = Modifier
                .padding(top = 35.dp, bottom = 30.dp)
                .height(80.dp)
                .fillMaxWidth()
//                .rotate(angle)
        )
    }
}