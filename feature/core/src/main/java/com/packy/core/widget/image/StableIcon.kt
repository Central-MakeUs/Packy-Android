package com.packy.core.widget.image

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun StableIcon(
    @DrawableRes imageRes: Int,
    tint:Color,
    modifier: Modifier = Modifier,
    contentDescription: String = "",
) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = imageRes),
        contentDescription = contentDescription,
        tint = tint
    )
}