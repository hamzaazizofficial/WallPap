package com.hamza.wallpap

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.paging.ExperimentalPagingApi
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.hamza.wallpap.ui.MainScreen
import com.hamza.wallpap.ui.animation.CircularReveal
import com.hamza.wallpap.ui.theme.WallPapTheme
import com.hamza.wallpap.util.ThemeSetting
import com.hamza.wallpap.util.ThemeSettingPreference
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var themeSetting: ThemeSetting

    @RequiresApi(Build.VERSION_CODES.N)
    @OptIn(ExperimentalPagingApi::class, ExperimentalComposeUiApi::class,
        ExperimentalAnimationApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
//                val splashScreen = installSplashScreen()
//                splashScreen.setKeepOnScreenCondition { true }
//            }
            themeSetting = ThemeSettingPreference(context = LocalContext.current)
            val theme = themeSetting.themeStream.collectAsState()
            val useDarkColors = when (theme.value) {
//                com.hamza.wallpap.util.WallPapTheme.MODE_AUTO -> isSystemInDarkTheme()
                com.hamza.wallpap.util.WallPapTheme.MODE_DAY -> false
                com.hamza.wallpap.util.WallPapTheme.MODE_NIGHT -> true
            }

            val isSystemDark = isSystemInDarkTheme()
            var darkTheme by remember { mutableStateOf(isSystemDark) }
            val onThemeToggle = { darkTheme = !darkTheme }
            val navController = rememberAnimatedNavController()

            CircularReveal(
                targetState = useDarkColors,
                animationSpec = tween(1800)
            ) { isDark ->
                WallPapTheme(darkTheme = isDark) {
                    Surface( modifier = Modifier.semantics { testTagsAsResourceId = true },
                        color = MaterialTheme.colors.background) {
                        MainScreen(
                            navController = navController,
                            onItemSelected = { theme -> themeSetting.theme = theme })
                    }
                }
            }
        }
    }
}
