package com.hamza.wallpap.ui.theme

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)
val HeartRed = Color(0xFFFF4444)
val lightTopBarBg = Color(0xFFCEE3FB)
val transparentStatusBarColor = Color(0xFFFFFF)

val Colors.navDrawerBgColor: Color
    get() = if (isLight) Color.LightGray.copy(0.4f) else Color.LightGray.copy(0.1f)

val Colors.navOptionContentColor: Color
    get() = if (isLight) Color.White else Color.Black

val Colors.navOptionIconColor: Color
    get() = if (isLight) Color.White else Color.Gray

val Colors.topAppBarTitle: Color
    get() = if (isLight) Color(0xFF474747) else Color.LightGray

val Colors.topAppBarContentColor: Color
    get() = if (isLight) Color.Gray else Color.LightGray

val Colors.topAppBarBackgroundColor: Color
    get() = if (isLight) Color.White else Color(0xFF243447)

val Colors.systemBarColor: Color
    get() = if (isLight) Color.White else Color(0xFF243447)

val Colors.bottomAppBarContentColor: Color
    get() = if (isLight) Color(0xFFFF5252) else Color(0xFFF50057)

val Colors.bottomAppBarBackgroundColor: Color
    get() = if (isLight) Color.White else Color(0xFF243447)

val Colors.textColor: Color
    get() = if (isLight) Color.Black else Color.White

val Colors.iconColor: Color
    get() = if (isLight) Color.Gray else Color.White