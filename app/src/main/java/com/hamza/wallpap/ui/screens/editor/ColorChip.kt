package com.hamza.wallpap.ui.screens.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.hamza.wallpap.ui.theme.bottomAppBarContentColor
import com.hamza.wallpap.ui.theme.textColor

@Composable
fun ColorChips(
    color: Color,
    selected: Boolean,
    onClick: () -> Unit,
) {
    if (selected) {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(6.dp)
                .size(40.dp)
                .clip(CircleShape),
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .border(4.dp, MaterialTheme.colors.bottomAppBarContentColor, CircleShape)
                    .background(color)
                    .clickable {
                        onClick()
                    }
            )
        }
    } else {
        Card(
            shape = CircleShape,
            modifier = Modifier
                .padding(6.dp)
                .size(40.dp)
                .clip(CircleShape),
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(0.5.dp, MaterialTheme.colors.textColor, CircleShape)
                    .background(color)
                    .clickable {
                        onClick()
                    }
            )
        }
    }
}