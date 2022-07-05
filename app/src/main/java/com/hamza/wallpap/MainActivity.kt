package com.hamza.wallpap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.hamza.wallpap.ui.MainScreen
import com.hamza.wallpap.ui.theme.WallPapTheme
import com.hamza.wallpap.util.ThemeSetting
import com.hamza.wallpap.util.ThemeSettingPreference
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //    @Inject
    private lateinit var themeSetting: ThemeSetting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            themeSetting = ThemeSettingPreference(context = LocalContext.current)
            val theme = themeSetting.themeStream.collectAsState()
            val useDarkColors = when (theme.value) {
                com.hamza.wallpap.util.WallPapTheme.MODE_AUTO -> isSystemInDarkTheme()
                com.hamza.wallpap.util.WallPapTheme.MODE_DAY -> false
                com.hamza.wallpap.util.WallPapTheme.MODE_NIGHT -> true
            }
            WallPapTheme(darkTheme = useDarkColors) {
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    MainScreen(navController = navController, onItemSelected = {theme-> themeSetting.theme = theme})
                }
            }
        }
    }
}
