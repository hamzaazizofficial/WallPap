package com.hamza.wallpap.ui.screens.editor

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                contentColor = MaterialTheme.colors.onSecondary,
                backgroundColor = MaterialTheme.colors.secondary
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = text,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }

//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier.background(MaterialTheme.colors.secondary)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.letter_w),
//                contentDescription = null,
//                modifier = Modifier
//                    .height(50.dp)
//                    .width(100.dp)
//                    .blur(4.dp)
//            )
//
//            Text(text = text, fontSize = 12.sp)
    } else {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier.background(MaterialTheme.colors.secondary.copy(0.1f))
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.letter_w),
//                contentDescription = null,
//                modifier = Modifier
//                    .height(50.dp)
//                    .width(100.dp)
//                    .blur(4.dp)
//            )
//
//
//            Text(text = text, fontSize = 12.sp)
//        }
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