package com.hamza.wallpap.ui.screens.common

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.hamza.wallpap.BuildConfig
import com.hamza.wallpap.ui.screens.settings.SettingsViewModel
import com.hamza.wallpap.ui.theme.iconColor
import com.hamza.wallpap.ui.theme.maven_pro_regular
import com.hamza.wallpap.ui.theme.navDrawerBgColor
import com.hamza.wallpap.ui.theme.textColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun NavDrawer(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    scope: CoroutineScope,
) {
    val context = LocalContext.current
    var settingsViewModel: SettingsViewModel = viewModel()

    if (settingsViewModel.dialogState.value) {
        GetProDialog(dialogState = settingsViewModel.dialogState, context = context)
    }

    if (settingsViewModel.dialogStateAbout.value) {
        AboutDialog(dialogState = settingsViewModel.dialogStateAbout, context = context)
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxHeight()
    ) {

        val constraints = ConstraintSet {
            val appName = createRefFor("appname")
            val bgNavImg = createRefFor("bgnavimg")
            val appLogo = createRefFor("logo")

            constrain(bgNavImg) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }

            constrain(appName) {
                top.linkTo(bgNavImg.top)
                start.linkTo(bgNavImg.start)
                bottom.linkTo(bgNavImg.bottom)
            }

            constrain(appLogo) {
                top.linkTo(bgNavImg.top)
                start.linkTo(bgNavImg.start)
                bottom.linkTo(bgNavImg.bottom)
            }
        }

        ConstraintLayout(constraints, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.navDrawerBgColor
                    )
                    .height(80.dp)
                    .layoutId("bgnavimg")
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp)
                    .layoutId("appname")
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF243447),
                                fontSize = 29.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = maven_pro_regular
                            )
                        ) {
                            append("Wall")
                        }

                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFFFF5252),
                                fontSize = 29.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = maven_pro_regular
                            )
                        ) {
                            append("Pap")
                        }
                    })

                AnimatedVisibility(
                    visible = scaffoldState.drawerState.isOpen,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { scope.launch { scaffoldState.drawerState.close() } }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color(0xFF243447)
                        )
                    }
                }
            }
        }

        NavOption(
            title = "Rate the App",
            scaffoldState = scaffoldState,
            Icons.Default.Star
        )

        NavOption(
            title = "Share the App",
            scaffoldState = scaffoldState,
            Icons.Default.Share
        )

        NavOption(
            title = "Remove ads",
            scaffoldState = scaffoldState,
            Icons.Default.DoNotDisturb,
            modifier = Modifier.clickable {
                settingsViewModel.dialogState.value = true
            }
        )

        NavOption(
            title = "About Us",
            scaffoldState = scaffoldState,
            Icons.Default.Info,
            modifier = Modifier.clickable {
                settingsViewModel.dialogStateAbout.value = true
            }
        )

        NavOption(
            title = "Settings",
            scaffoldState = scaffoldState,
            Icons.Default.Settings,
            modifier = Modifier.clickable {
                navController.navigate("settings_screen")
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        )

        Divider(color = MaterialTheme.colors.iconColor.copy(0.5f))

        Text(
            text = "More",
            color = MaterialTheme.colors.textColor,
            fontSize = 17.sp,
            style = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontFamily = maven_pro_regular
            ),
            modifier = Modifier
                .padding(start = 12.dp, top = 14.dp, bottom = 8.dp)
        )

        NavOption(
            title = "Email Us",
            scaffoldState = scaffoldState,
            Icons.Default.Email,
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf("9ine.kt@gmail.com")
                )
                intent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "WallPap version: ${BuildConfig.VERSION_NAME}"
                )
                try {
                    startActivity(context, intent, null)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            })

        NavOption(
            title = "More Apps",
            scaffoldState = scaffoldState,
            Icons.Default.Apps,
            modifier = Modifier.clickable {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data =
                    Uri.parse("https://play.google.com/store/apps/dev?id=7870775867932667955")
                try {
                    startActivity(context, intent, null)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        )
    }
}

@Composable
fun NavOption(
    title: String,
    scaffoldState: ScaffoldState,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier
            .clickable(onClick = {
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
    )
    {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.padding(10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colors.iconColor,
            )
            Spacer(modifier = Modifier.padding(end = 20.dp))
            Text(
                text = title,
                color = MaterialTheme.colors.textColor,
                fontSize = 16.sp,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp)
            )
        }
    }
}

