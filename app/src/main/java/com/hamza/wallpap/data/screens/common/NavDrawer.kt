package com.hamza.wallpap.data.screens.common

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.core.content.ContextCompat.startActivity
import com.hamza.wallpap.BuildConfig
import com.hamza.wallpap.R
import com.hamza.wallpap.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun NavDrawer(scaffoldState: ScaffoldState) {
    val context = LocalContext.current

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
//            Image(
//                painterResource(id = R.drawable.picture),
//                contentDescription = null,
//                contentScale = ContentScale.Fit,
////                colorFilter = ColorFilter.tint(Color.Black),
//                modifier = Modifier
//                    .padding(start = 30.dp, top = 2.dp, bottom = 16.dp)
//                    .height(70.dp)
//                    .layoutId("logo")
//            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 30.dp)
                    .layoutId("appname")
            ) {
                Text(
                    text = "WallPap",
                    fontSize = 29.sp,
                    color = MaterialTheme.colors.textColor,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        fontWeight = FontWeight.Bold,
                        fontFamily = maven_pro_regular
                    ),
                    modifier = Modifier
                )
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
            Icons.Default.DoNotDisturb
        )

        NavOption(
            title = "About Us",
            scaffoldState = scaffoldState,
            Icons.Default.Info,
            modifier = Modifier.clickable { }
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

