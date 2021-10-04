package github.sukieva.hhu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.TravelExplore
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import github.sukieva.hhu.ui.theme.fontBody
import github.sukieva.hhu.ui.theme.fontHead



@Composable
fun MyListItem(
    modifier: Modifier = Modifier,
    title: String = "Title",
    icon: ImageVector = Icons.Outlined.TravelExplore,
    onClick: () -> Unit = {},
){
    ListCard(
        modifier = modifier
            .padding(start = 5.dp, end = 5.dp, top = 12.dp,bottom = 12.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClick() },
        title = title,
        icon = icon
    )
}





@Composable
fun ListCardItem(
    modifier: Modifier = Modifier,
    title: String = "Title",
    icon: ImageVector = Icons.Outlined.TravelExplore,
    onClick: () -> Unit = {},
) {
    ListCard(
        modifier = modifier
            .padding(start = 25.dp, end = 25.dp, top = 20.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClick() },
        title = title,
        icon = icon
    )
}


@Composable
fun ListCard(
    modifier: Modifier = Modifier,
    title: String = "Title",
    icon: ImageVector = Icons.Outlined.TravelExplore,
    //onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        backgroundColor = MaterialTheme.colors.background,
        elevation = 0.dp
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
                    .size(33.dp),
                imageVector = icon,
                contentDescription = "List icon",
                colorFilter = ColorFilter.tint(color = MaterialTheme.colors.fontBody)
            )
            Spacer(modifier = Modifier.width(35.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colors.fontHead,
                    style = MaterialTheme.typography.h1
                )
            }
        }
    }
}

@Preview
@Composable
fun ListCardItemPreview() {
    ListCardItem()
}