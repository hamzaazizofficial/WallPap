package com.hamza.wallpap

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
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
//    val activity: Activity = Activity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                val w: Window = window
                w.setFlags(
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                )
            }
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
