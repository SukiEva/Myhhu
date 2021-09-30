package github.sukieva.hhu.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
fun MaterialBody(
    text: String,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        color = color,
        style = MaterialTheme.typography.body1
    )
}


@Composable
fun MaterialHead(
    text: String,
    color: Color = Color.Unspecified
) {
    Text(
        text = text,
        style = MaterialTheme.typography.h6,
        color = color
    )
}

@Composable
fun MaterialSub(
    text: String,
    color: Color = Color.Unspecified

) {
    Text(
        text = text,
        style = MaterialTheme.typography.subtitle2,
        color = color
    )
}