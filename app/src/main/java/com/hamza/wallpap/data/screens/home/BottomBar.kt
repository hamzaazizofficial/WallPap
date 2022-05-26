package com.hamza.wallpap.data.screens.home

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar() {

    val selectedItem = remember { mutableStateOf("home") }

    BottomNavigation(
        backgroundColor = Color.Black,
        contentColor = Color.White
    ) {

        BottomNavigationItem(
            icon = {
                Icon(Icons.Filled.Home, contentDescription = "")
            },
            label = { Text(text = "Home") },
            selected = selectedItem.value == "home", onClick = {
                selectedItem.value = "home"
            },
            alwaysShowLabel = false
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Filled.Whatshot, null,
                )
            },
            label = { Text(text = "Hot") },
            selected = selectedItem.value == "hot",
            onClick = {
                selectedItem.value = "hot"
            },
            alwaysShowLabel = false
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Filled.Favorite, "",
                )
            },
            label = { Text(text = "Favorite") },
            selected = selectedItem.value == "favorite",
            onClick = {
                selectedItem.value = "favorite"
            },
            alwaysShowLabel = false
        )

        BottomNavigationItem(
            icon = {
                Icon(
                    Icons.Filled.Settings, "",
                )
            },
            label = { Text(text = "Settings") },
            selected = selectedItem.value == "settings",
            onClick = {

                selectedItem.value = "settings"
            },
            alwaysShowLabel = false
        )
    }
}