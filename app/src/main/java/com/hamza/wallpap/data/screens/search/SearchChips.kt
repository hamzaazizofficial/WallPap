package com.hamza.wallpap.data.screens.search

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hamza.wallpap.ui.theme.textColor

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
                backgroundColor = MaterialTheme.colors.secondary.copy(0.1f),
                contentColor = MaterialTheme.colors.textColor
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = text, fontSize = 12.sp)
        }
    }
}