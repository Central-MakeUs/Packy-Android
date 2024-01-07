package com.packy.core

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

@Composable
fun PackyTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        PackyColorScheme provides LightPackyColor(),
        PackyTypographyScheme provides DefaultPackyTypography()
    ) {
        MaterialTheme {
            content()
        }
    }
}

object PackyTheme {
    val color: PackyColor
        @Composable
        @ReadOnlyComposable
        get() = PackyColorScheme.current

    val typography: PackyTypography
        @Composable
        @ReadOnlyComposable
        get() = PackyTypographyScheme.current
}