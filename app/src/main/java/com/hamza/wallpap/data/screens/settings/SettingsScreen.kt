package com.hamza.wallpap.data.screens.settings

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hamza.wallpap.BuildConfig
import com.hamza.wallpap.ui.theme.iconColor
import com.hamza.wallpap.ui.theme.systemBarColor
import com.hamza.wallpap.ui.theme.textColor
import com.hamza.wallpap.util.WallPapTheme
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    onItemSelected: (WallPapTheme) -> Unit
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.systemBarColor)

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val dataStore = DataStorePreferenceRepository(context)
    val themeValue = dataStore.getThemeValue.collectAsState(initial = 0)

    BackHandler {
        if (scaffoldState.drawerState.isOpen) {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        } else {
            navController.navigate("home_screen")
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colors.background)
                .padding(12.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Theme",
                    color = MaterialTheme.colors.textColor,
                    fontSize = 14.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
//                                    fontFamily = abel_regular
                    ),
                    modifier = Modifier.weight(1f)
                )

                if (themeValue.value == 0) {
                    Icon(
                        imageVector = Icons.Filled.DarkMode,
                        tint = Color.Yellow,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onItemSelected(WallPapTheme.fromOrdinal(WallPapTheme.MODE_DAY.ordinal))
                            scope.launch {
                                dataStore.saveThemeValue(1)
                            }
                        })
                }

                if (themeValue.value == 1) {
                    Icon(
                        imageVector = Icons.Outlined.DarkMode,
                        tint = MaterialTheme.colors.iconColor,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onItemSelected(WallPapTheme.fromOrdinal(WallPapTheme.MODE_NIGHT.ordinal))
                            scope.launch {
                                dataStore.saveThemeValue(0)
                            }
                        })
                }
            }

            Spacer(modifier = Modifier.padding(6.dp))
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            Spacer(modifier = Modifier.padding(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Tired of Ads?",
                    color = MaterialTheme.colors.textColor,
                    fontSize = 14.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
//                                    fontFamily = abel_regular
                    ),
                    modifier = Modifier
                )
                Text(
                    text = "Remove Ads now!",
                    fontSize = 14.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.body1.fontStyle,
//                                    fontFamily = abel_regular
                    ),
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier.clickable {
                        AppCompatDelegate.setDefaultNightMode(
                            AppCompatDelegate.MODE_NIGHT_NO
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.padding(6.dp))
            Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
            Spacer(modifier = Modifier.padding(6.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(2.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "App Version",
                    color = MaterialTheme.colors.textColor,
                    fontSize = 14.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
//                                    fontFamily = abel_regular
                    ),
                    modifier = Modifier
                )
                Text(
                    text = "${BuildConfig.VERSION_CODE}",
                    color = MaterialTheme.colors.textColor,
                    fontSize = 14.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.body1.fontStyle,
//                                    fontFamily = abel_regular
                    ),
                    modifier = Modifier
                )
            }
        }
    }
}