package com.hamza.wallpap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.hamza.wallpap.ui.MainScreen
import com.hamza.wallpap.ui.theme.WallPapTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WallPapTheme {
                val navController = rememberNavController()
                MainScreen(navController = navController)
            }
        }
    }
}
