package github.sukieva.hhu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material.icons.outlined.Extension
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import github.sukieva.hhu.R
import github.sukieva.hhu.ui.activity.ConfigActivity
import github.sukieva.hhu.ui.activity.FavouriteActivity
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.theme.fontBody
import github.sukieva.hhu.ui.theme.fontHead
import github.sukieva.hhu.utils.start


@Composable
fun ListCardFavourite() {
    ListCardItem(
        title = stringResource(id = R.string.home_list_favourite),
        icon = Icons.Outlined.Bookmarks,
        onClick = {
            start<FavouriteActivity>()
        }
    )
}

@Composable
fun ListCardConfig() {
    ListCardItem(
        title = stringResource(id = R.string.home_list_config),
        icon = Icons.Outlined.Extension,
        onClick = {
            start<ConfigActivity>()
        }
    )
}


@Composable
fun ListCardItem(
    modifier: Modifier = Modifier,
    title: String = "Title",
    icon: ImageVector = Icons.Outlined.CheckCircleOutline,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .padding(start = 25.dp, end = 25.dp, top = 20.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable { onClick() },
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
fun ListPreview() {
    InitView {
        ListCardItem()
    }
}