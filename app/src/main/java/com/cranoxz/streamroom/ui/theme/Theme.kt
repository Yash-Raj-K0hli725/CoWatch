package com.cranoxz.streamroom.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color.hsl(176f, 0.49f, 0.56f),
    secondary = Color.hsl(351f, 0.65f, 0.75f),
    tertiary = Color.hsl(175f, 1f, 0.89f),
    background = Color.hsl(175f, 1f, 0.01f)
    /*
    --bg-dark: hsl(176 100% 1%);
    --bg: hsl(176 100% 3%);
    --bg-light: hsl(177 78% 6%);
    --text: hsl(175 100% 89%);
    --text-muted: hsl(175 31% 63%);
    --highlight: hsl(176 35% 32%);
    --border: hsl(177 58% 20%);
    --border-muted: hsl(178 100% 8%);
    --primary: hsl(176 49% 56%);
    --secondary: hsl(351 65% 75%);
    --danger: hsl(9 31% 65%);
    --warning: hsl(52 22% 56%);
    --success: hsl(147 21% 58%);
    --info: hsl(217 34% 65%);
    */
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
    /*
    --bg-dark: hsl(175 42% 87%);
    --bg: hsl(175 70% 92%);
    --bg-light: hsl(175 100% 97%);
    --text: hsl(178 100% 1%);
    --text-muted: hsl(177 58% 20%);
    --highlight: hsl(175 100% 94%);
    --border: hsl(176 25% 44%);
    --border-muted: hsl(176 25% 56%);
    --primary: hsl(178 100% 8%);
    --secondary: hsl(348 44% 31%);
    --danger: hsl(9 25% 41%);
    --warning: hsl(51 29% 33%);
    --success: hsl(148 24% 35%);
    --info: hsl(217 26% 42%);
    */

)

@Composable
fun CoWatchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
//        when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}