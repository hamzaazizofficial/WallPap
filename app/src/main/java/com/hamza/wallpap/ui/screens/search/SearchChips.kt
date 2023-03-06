package com.hamza.wallpap.ui.screens.search

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hamza.wallpap.ui.theme.textColor

@Composable
fun SearchChips(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    if (selected) {
        TextButton(
            modifier = Modifier.padding(vertical = 6.dp),
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
//            )
//
//            Text(text = text, fontSize = 12.sp)
//        }
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
//            )

//
//            Text(text = text, fontSize = 12.sp)
//        }
        TextButton(
            modifier = Modifier.padding(vertical = 6.dp),
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