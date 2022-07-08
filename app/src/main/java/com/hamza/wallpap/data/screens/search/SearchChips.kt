package com.hamza.wallpap.data.screens.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchChips(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    if (selected) {
        Button(
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colors.onSecondary,
                backgroundColor = MaterialTheme.colors.secondary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = text, fontSize = 12.sp)
        }
    } else {
        TextButton(
            onClick = {
                onClick()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary.copy(0.8f),
                contentColor = MaterialTheme.colors.onSecondary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = text, fontSize = 12.sp)
        }
    }
}