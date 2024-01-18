package com.packy.core.theme

import android.annotation.SuppressLint
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

interface PackyColor {
    val black: Color
    val gray900: Color
    val gray800: Color
    val gray700: Color
    val gray600: Color
    val gray500: Color
    val gray400: Color
    val gray300: Color
    val gray200: Color
    val gray100: Color
    val white: Color
    val purple800: Color
    val purple700: Color
    val purple600: Color
    val purple500: Color
    val purple400: Color
    val purple300: Color
    val purple200: Color
    val purple100: Color
    val pink800: Color
    val pink700: Color
    val pink600: Color
    val pink500: Color
    val pink400: Color
    val pink300: Color
    val pink200: Color
    val pink100: Color
    val errorText: Color
}

internal val lightPackyColor = object: PackyColor {
    override val black: Color = Color(0xFF000000)
    override val gray900: Color = Color(0xFF222222)
    override val gray800: Color = Color(0xFF444444)
    override val gray700: Color = Color(0xFF717171)
    override val gray600: Color = Color(0xFF888888)
    override val gray500: Color = Color(0xFFA6A6A6)
    override val gray400: Color = Color(0xFFBBBBBB)
    override val gray300: Color = Color(0xFFDDDDDD)
    override val gray200: Color = Color(0xFFE9E9E9)
    override val gray100: Color = Color(0xFFF3F3F3)
    override val white: Color = Color(0xFFFFFFFF)
    override val purple800: Color = Color(0xFF2F2F7C)
    override val purple700: Color = Color(0xFF3C37A4)
    override val purple600: Color = Color(0xFF4545D1)
    override val purple500: Color = Color(0xFF635DF0)
    override val purple400: Color = Color(0xFF7979F7)
    override val purple300: Color = Color(0xFFA4A1FF)
    override val purple200: Color = Color(0xFFCACAFC)
    override val purple100: Color = Color(0xFFEBEAFF)
    override val pink800: Color = Color(0xFFAF3893)
    override val pink700: Color = Color(0xFFD44B81)
    override val pink600: Color = Color(0xFFED76A5)
    override val pink500: Color = Color(0xFFF594BA)
    override val pink400: Color = Color(0xFFFFB2D0)
    override val pink300: Color = Color(0xFFFFC5DC)
    override val pink200: Color = Color(0xFFFFD5E5)
    override val pink100: Color = Color(0xFFFFE8F1)
    override val errorText: Color = Color(0xFFF34248)
}

@SuppressLint("CompositionLocalNaming")
internal val PackyColorScheme = staticCompositionLocalOf { lightPackyColor }