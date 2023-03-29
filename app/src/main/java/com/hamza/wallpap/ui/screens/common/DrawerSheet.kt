package com.hamza.wallpap.ui.screens.common

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.hamza.wallpap.BuildConfig
import com.hamza.wallpap.ui.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun DrawerSheet(
    mainViewModel: MainViewModel,
    scope: CoroutineScope,
    navController: NavHostController,
    drawerState: DrawerState,
    currentRoute: String?,
    context: Context,
) {
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        drawerShape = RoundedCornerShape(
            topStart = 0.dp,
            bottomStart = 0.dp,
            topEnd = 30.dp,
            bottomEnd = 30.dp
        ),
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
                    .height(100.dp)
                    .fillMaxWidth()
                    .padding(0.dp)
                    .padding(0.dp)
                    .layoutId("bgnavimg")
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp, vertical = 30.dp)
                        .layoutId("appname")
                ) {

                    Text(
                        textAlign = TextAlign.Justify,
                        text = "Dreamy",
                        color = Color(0xFF193144),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp,
                        modifier = Modifier
                            .padding(start = 22.dp, end = 10.dp),
                    )

                    AnimatedVisibility(
                        visible = drawerState.isOpen,
                        enter = scaleIn() + fadeIn(),
                        exit = scaleOut()
                    ) {
                        IconButton(onClick = { scope.launch { drawerState.close() } }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        NavigationDrawerItem(
            colors = NavigationDrawerItemDefaults.colors(
//                selectedContainerColor = MaterialTheme.colors.homeColumnGradient1,
//                unselectedContainerColor = MaterialTheme.colors.unselectedDrawerContainerColor,
//                selectedTextColor = MaterialTheme.colors.textColor,
//                selectedIconColor = MaterialTheme.colors.textColor,
//                unselectedTextColor = MaterialTheme.colors.textColor,
//                unselectedIconColor = MaterialTheme.colors.textColor,
            ),
            icon = { Icon(Icons.Default.Home, contentDescription = null) },
            label = { Text("Home") },
            selected = mainViewModel.selectedItem.value == mainViewModel.items[0],
            onClick = {
//                if (!currentRoute.equals(Screen.OnBoarding.route)) {
//                    navController.navigate("home")
//                }
                scope.launch {
                    drawerState.close()
                }
                mainViewModel.selectedItem.value = mainViewModel.items[0]
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        Spacer(Modifier.height(8.dp))

        NavigationDrawerItem(
            colors = NavigationDrawerItemDefaults.colors(
//                selectedContainerColor = MaterialTheme.colors.homeColumnGradient1,
//                unselectedContainerColor = MaterialTheme.colors.unselectedDrawerContainerColor,
//                selectedTextColor = MaterialTheme.colors.textColor,
//                selectedIconColor = MaterialTheme.colors.textColor,
//                unselectedTextColor = MaterialTheme.colors.textColor,
//                unselectedIconColor = MaterialTheme.colors.textColor,
            ),
            icon = { Icon(Icons.Default.Favorite, contentDescription = null) },
            label = { Text("Favourites") },
            selected = mainViewModel.selectedItem.value == mainViewModel.items[1],
            onClick = {
//                if (!currentRoute.equals(Screen.Home.route)) {
//                    navController.navigate("home")
//                }
                scope.launch {
                    drawerState.close()
                }
                mainViewModel.selectedItem.value = mainViewModel.items[1]
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        Spacer(Modifier.height(8.dp))

        NavigationDrawerItem(
            colors = NavigationDrawerItemDefaults.colors(
//                selectedContainerColor = MaterialTheme.colors.homeColumnGradient1,
//                unselectedContainerColor = MaterialTheme.colors.unselectedDrawerContainerColor,
//                selectedTextColor = MaterialTheme.colors.textColor,
//                selectedIconColor = MaterialTheme.colors.textColor,
//                unselectedTextColor = MaterialTheme.colors.textColor,
//                unselectedIconColor = MaterialTheme.colors.textColor,
            ),
            icon = { Icon(Icons.Default.Settings, contentDescription = null) },
            label = { Text("Settings") },
            selected = mainViewModel.selectedItem.value == mainViewModel.items[2],
            onClick = {
//                if (!currentRoute.equals(Screen.Settings.route)) {
//                    navController.navigate("settings")
//                }
                scope.launch { drawerState.close() }
                mainViewModel.selectedItem.value = mainViewModel.items[2]
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        Spacer(Modifier.height(8.dp))

        NavigationDrawerItem(
            colors = NavigationDrawerItemDefaults.colors(
//                selectedContainerColor = MaterialTheme.colors.homeColumnGradient1,
//                unselectedContainerColor = MaterialTheme.colors.unselectedDrawerContainerColor,
//                selectedTextColor = MaterialTheme.colors.textColor,
//                selectedIconColor = MaterialTheme.colors.textColor,
//                unselectedTextColor = MaterialTheme.colors.textColor,
//                unselectedIconColor = MaterialTheme.colors.textColor,
            ),
            icon = { Icon(Icons.Default.Email, contentDescription = null) },
            label = { Text("Email Us") },
            selected = mainViewModel.selectedItem.value == mainViewModel.items[3],
            onClick = {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(
                    Intent.EXTRA_EMAIL,
                    arrayOf("9ine.kt@gmail.com")
                )
                intent.putExtra(
                    Intent.EXTRA_SUBJECT,
                    "NoClue version: ${BuildConfig.VERSION_NAME}"
                )
                try {
                    ContextCompat.startActivity(context, intent, null)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
                scope.launch { drawerState.close() }
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        Spacer(Modifier.height(8.dp))

        NavigationDrawerItem(
            colors = NavigationDrawerItemDefaults.colors(
//                selectedContainerColor = MaterialTheme.colors.homeColumnGradient1,
//                unselectedContainerColor = MaterialTheme.colors.unselectedDrawerContainerColor,
//                selectedTextColor = MaterialTheme.colors.textColor,
//                selectedIconColor = MaterialTheme.colors.textColor,
//                unselectedTextColor = MaterialTheme.colors.textColor,
//                unselectedIconColor = MaterialTheme.colors.textColor,
            ),
            icon = { Icon(Icons.Default.Apps, contentDescription = null) },
            label = { Text("More Apps") },
            selected = mainViewModel.selectedItem.value == mainViewModel.items[4],
            onClick = {
                scope.launch { drawerState.close() }
                playStoreIntent(
                    "https://play.google.com/store/apps/dev?id=7870775867932667955&hl=en&gl=US",
                    context
                )
            },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )

        Spacer(Modifier.height(8.dp))
    }
}

fun playStoreIntent(uri: String, context: Context) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data =
        Uri.parse(uri)
    try {
        ContextCompat.startActivity(context, intent, null)
    } catch (e: Exception) {
        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
    }
}