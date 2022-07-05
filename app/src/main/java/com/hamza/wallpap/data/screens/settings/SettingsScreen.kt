package com.hamza.wallpap.data.screens.settings

import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
    var themeExpanded by remember { mutableStateOf(false) }
    val themes = settingsViewModel.themes

    var themeExpandedState by remember {
        mutableStateOf(false)
    }
    val themeDropDownRotationState by animateFloatAsState(
        targetValue = if (themeExpandedState) 180f else 0f
    )

    BackHandler {
        if (scaffoldState.drawerState.isOpen) {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        } else {
            navController.navigate("home_screen")
        }
    }

//    if (settingsViewModel.value.value == themes[1]) {
//        onItemSelected(WallPapTheme.fromOrdinal(WallPapTheme.MODE_NIGHT.ordinal))
////        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
////        ThemeState.isLight = false
//
//    }
////
//    if (settingsViewModel.value.value == themes[0]) {
//        onItemSelected(WallPapTheme.fromOrdinal(WallPapTheme.MODE_DAY.ordinal))
////        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
////        ThemeState.isLight = true
//    }

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

                Row() {
                    IconButton(onClick = { onItemSelected(WallPapTheme.fromOrdinal(WallPapTheme.MODE_NIGHT.ordinal)) }) {
                        Icon(imageVector = Icons.Default.LightMode, contentDescription = null)
                    }
                    IconButton(onClick = { onItemSelected(WallPapTheme.fromOrdinal(WallPapTheme.MODE_DAY.ordinal)) }) {
                        Icon(imageVector = Icons.Default.DarkMode, contentDescription = null)
                    }
                }
//                Row(
//                    modifier = Modifier.clickable {
//                        themeExpanded = true
//                        themeExpandedState = !themeExpandedState
//                    },
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = settingsViewModel.value.value,
//                        fontSize = 14.sp,
//                        style = TextStyle(
//                            fontStyle = MaterialTheme.typography.body1.fontStyle,
////                                        fontFamily = abel_regular
//                        ),
//                        modifier = Modifier
//                    )
//                    Icon(
//                        imageVector = Icons.Default.ArrowDropDown,
//                        contentDescription = null,
//                        modifier = Modifier.rotate(themeDropDownRotationState)
//                    )
//                    if (themeExpanded) {
//                        DropdownMenu(
//                            expanded = themeExpanded,
//                            onDismissRequest = {
//                                themeExpanded = false
//                                themeExpandedState = !themeExpandedState
//                            }
//                        ) {
//                            themes.forEach { selectionOption ->
//                                DropdownMenuItem(
//                                    onClick = {
//                                        settingsViewModel.value.value = selectionOption
//                                        themeExpanded = false
//                                        themeExpandedState = !themeExpandedState
//                                    }
//                                ) {
//                                    Text(
//                                        text = selectionOption,
////                                                    fontFamily = abel_regular,
//                                        maxLines = 1,
//                                        overflow = TextOverflow.Ellipsis
//                                    )
//                                }
//                            }
//                        }
//                    }
//                }
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