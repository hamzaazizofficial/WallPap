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
import androidx.compose.material.icons.filled.LightMode
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
import com.hamza.wallpap.BuildConfig
import com.hamza.wallpap.util.WallPapTheme
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    navController: NavHostController,
    scaffoldState: ScaffoldState,
    onItemSelected: (WallPapTheme) -> Unit
) {
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
                .background(Color.Green)
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
                    fontSize = 14.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
//                                    fontFamily = abel_regular
                    ),
                    modifier = Modifier.weight(1f)
                )

                if (themeValue.value == 0) {
                    Icon(
                        imageVector = Icons.Default.LightMode,
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
                        imageVector = Icons.Default.DarkMode,
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
                    fontSize = 14.sp,
                    style = TextStyle(
                        fontStyle = MaterialTheme.typography.subtitle1.fontStyle,
//                                    fontFamily = abel_regular
                    ),
                    modifier = Modifier
                )
                Text(
                    text = "${BuildConfig.VERSION_CODE}",
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