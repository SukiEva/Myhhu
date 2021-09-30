package github.sukieva.hhu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.theme.*

@ExperimentalMaterialApi
@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    title: String = "Title",
    body: String = "Body",
    icon: ImageVector = Icons.Outlined.CheckCircleOutline,
    onClick: () -> Unit = {},
    isLarge: Boolean = false,
    isActive: Boolean = false
) {
    val cardHeight = if (isLarge) 100.dp else 80.dp
    Card(
        modifier = modifier
            .padding(start = 15.dp, end = 15.dp, top = 10.dp)
            .fillMaxWidth()
            .height(cardHeight),
        elevation = 0.5.dp,
        shape = MaterialTheme.shapes.small,
        onClick = onClick,
        backgroundColor = when {
            isLarge && isActive -> MaterialTheme.colors.cardFlash
            isLarge -> Color.Gray
            else -> MaterialTheme.colors.card
        }
    ) {
        Row(
            modifier = Modifier
                .padding(all = 8.dp)
                .fillMaxWidth()
                .padding(start = 10.dp)
        ) {
            Image(
                modifier = Modifier
                    .fillMaxHeight()
                    .size(30.dp),
                imageVector = icon,
                contentDescription = "Card icon",
                colorFilter = ColorFilter.tint(color = if (isLarge) MaterialTheme.colors.fontFlash else MaterialTheme.colors.fontBody)
            )
            Spacer(modifier = Modifier.width(35.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 5.dp, bottom = 5.dp)
            ) {
                if (isLarge) Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = title,
                    color = if (isLarge) MaterialTheme.colors.fontFlash else MaterialTheme.colors.fontHead,
                    style = MaterialTheme.typography.h1
                )
                if (isLarge) Spacer(modifier = Modifier.height(10.dp))
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = body,
                    color = if (isLarge) MaterialTheme.colors.fontFlash else MaterialTheme.colors.fontBody,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}

@ExperimentalMaterialApi
@Preview(
//    showSystemUi = true
)
@Composable
fun CardPreview() {
    InitView {
        CardItem()
    }
}