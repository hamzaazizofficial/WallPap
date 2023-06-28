package com.hamza.wallpap.ui.screens.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.foundation.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
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
import androidx.navigation.NavHostController
import com.hamza.wallpap.BuildConfig
import com.hamza.wallpap.R
import com.hamza.wallpap.ui.screens.settings.SettingsViewModel
import com.hamza.wallpap.ui.theme.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun NavDrawer(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    scope: CoroutineScope,
    settingsViewModel: SettingsViewModel,
    context: Context,
) {
    if (settingsViewModel.dialogState.value) {
        GetProDialog(dialogState = settingsViewModel.dialogState, context = context)
    }

    if (settingsViewModel.dialogStateAbout.value) {
        AboutDialog(dialogState = settingsViewModel.dialogStateAbout)
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colors.background)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {

        val constraints = ConstraintSet {
            val appName = createRefFor("app_name")
            val bgNavImg = createRefFor("bg_nav_img")
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
                    .height(90.dp)
                    .layoutId("bg_nav_img")
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 0.dp)
                    .layoutId("app_name")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(horizontal = 26.dp)
                ) {
                    Image(
                        painterResource(id = R.drawable.wattpad),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(80.dp)
                            .padding(vertical = 10.dp)
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colors.iconColor,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = maven_pro_regular
                                )
                            ) {
                                append(stringResource(id = R.string.wall))
                            }

                            withStyle(
                                style = SpanStyle(
                                    color = Color(0xFFfe6c40),
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = maven_pro_regular
                                )
                            ) {
                                append(stringResource(id = R.string.pap))
                            }
                        }
                    )
                }

                AnimatedVisibility(
                    visible = scaffoldState.drawerState.isOpen,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    IconButton(onClick = { scope.launch { scaffoldState.drawerState.close() } }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colors.iconColor
                        )
                    }
                }
            }
        }

        NavOption(
            title = stringResource(id = R.string.rate_app),
            scaffoldState = scaffoldState,
            Icons.Default.Star
        )

        NavOption(
            title = stringResource(id = R.string.share_app),
            scaffoldState = scaffoldState,
            Icons.Default.Share
        )

        NavOption(
            title = stringResource(id = R.string.remove_ads),
            scaffoldState = scaffoldState,
            Icons.Default.DoNotDisturb,
            modifier = Modifier.clickable {
                settingsViewModel.dialogState.value = true
            }
        )

        NavOption(
            title = stringResource(id = R.string.about_us),
            scaffoldState = scaffoldState,
            Icons.Default.Info,
            modifier = Modifier.clickable {
                settingsViewModel.dialogStateAbout.value = true
            }
        )

        NavOption(
            title = stringResource(id = R.string.settings),
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
            text = stringResource(id = R.string.more),
            color = MaterialTheme.colors.topAppBarTitle,
            fontSize = 17.sp,
            style = TextStyle(
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontFamily = maven_pro_regular
            ),
            modifier = Modifier
                .padding(start = 12.dp, top = 14.dp, bottom = 8.dp)
        )

        NavOption(
            title = stringResource(id = R.string.email_us),
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
            title = stringResource(id = R.string.more_apps),
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
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(onClick = {
                scope.launch {
                    scaffoldState.drawerState.close()
                }
            })
    )
    {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = modifier.padding(14.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colors.iconColor,
            )
            Spacer(modifier = Modifier.padding(end = 20.dp))
            Text(
                text = title,
                color = MaterialTheme.colors.topAppBarTitle,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

