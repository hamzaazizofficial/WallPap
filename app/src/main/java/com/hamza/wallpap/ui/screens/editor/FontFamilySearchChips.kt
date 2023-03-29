package com.hamza.wallpap.ui.screens.editor

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hamza.wallpap.ui.theme.bottomAppBarContentColor
import com.hamza.wallpap.ui.theme.textColor

@Composable
fun FontFamilySearchChips(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    if (selected) {
        TextButton(
            modifier = Modifier.padding(vertical = 2.dp),
            onClick = { },
            colors = ButtonDefaults.buttonColors(
                //contentColor = MaterialTheme.colors.onSecondary,
                //backgroundColor = MaterialTheme.colors.secondary
                contentColor = Color.White,
                backgroundColor = MaterialTheme.colors.bottomAppBarContentColor
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    } else {
        TextButton(
            modifier = Modifier.padding(vertical = 2.dp),
            onClick = {
                onClick()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary.copy(0.1f),
                contentColor = MaterialTheme.colors.textColor
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}