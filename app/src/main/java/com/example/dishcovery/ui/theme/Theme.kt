package com.example.dishcovery.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF71361E),
    secondary = Color(0xFF5D4035),
    tertiary = Color(0xFF3B4507),
    background = Color(0xFF1A110E),
    onSecondary = Color(0xFFF1DFD9),
    error = Color(0xFF93000A),
    secondaryContainer = Color(0xFF7E584A),
    onPrimary = Color(0xFFFFF8F6)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF8F4C32),
    secondary = Color(0xFFEEDCD6),
    tertiary = Color(0xFF695E30),
    background = Color(0xFFFFF8F6),
    onSecondary = Color(0xFF392E2B),
    error = Color(0xFFBA1A1A),
    secondaryContainer = Color(0xFFFFF8F6),
    onPrimary = Color(0xFFFFF8F6)

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun DishcoveryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    //dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}