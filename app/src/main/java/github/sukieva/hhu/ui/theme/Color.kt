package github.sukieva.hhu.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val White = Color(0xffffffff)
val Black = Color(0xff000000)
val ui_background = Color(0xfffafafa)
val ui_background_night = Color(0xff212121)


const val AlphaNearOpaque = 0.95f

val Colors.card: Color @Composable get() = if (isLight) White else Color(0xff424242)
val Colors.cardFlash: Color @Composable get() = if (isLight) Color(0xff01579b) else Color(0xffb0bbc5)
val Colors.fontHead: Color @Composable get() = if (isLight) Color(0xff212121) else Color(0xffffffff)
val Colors.fontBody: Color @Composable get() = if (isLight) Color(0xff757575) else Color(0xffc7c7c7)
val Colors.fontFlash: Color @Composable get() = if (isLight) White else Black