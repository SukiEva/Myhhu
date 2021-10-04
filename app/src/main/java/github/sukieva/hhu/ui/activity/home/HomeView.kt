package github.sukieva.hhu.ui.activity.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Extension
import androidx.compose.material.icons.rounded.ManageSearch
import androidx.compose.material.icons.rounded.PendingActions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import github.sukieva.hhu.R
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.activity.config.ConfigActivity
import github.sukieva.hhu.ui.activity.favourite.FavouriteActivity
import github.sukieva.hhu.ui.activity.results.ResultsActivity
import github.sukieva.hhu.ui.components.*
import github.sukieva.hhu.ui.theme.Teal200
import github.sukieva.hhu.utils.infoToast
import github.sukieva.hhu.utils.start


@Composable
fun HomeView() {
    InitView {
        HomeAppBar()
        CardCheckIn()
        CardGradeQuery()
        CardSchedule()
        ListCardFavourite()
        ListCardConfig()
    }
}


@Composable
fun CardCheckIn() {
    CardItem(
        isLarge = true,
        isActive = true,
        onClick = { "点击了".infoToast() },
        title = stringResource(id = R.string.home_card_checkin),
        body = "已经打卡"
    )
}

@Composable
fun CardGradeQuery() {
    CardItem(
        title = stringResource(id = R.string.home_card_results),
        body = "加油",
        icon = Icons.Rounded.ManageSearch,
        onClick = {
            start<ResultsActivity>()
        }
    )
}

@Composable
fun CardSchedule() {
    CardItem(
        title = stringResource(id = R.string.home_card_schedule),
        body = "今天无课",
        icon = Icons.Rounded.PendingActions
    )
}


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
fun HomeAppBar() {
    MaterialAppBar(
        modifier = Modifier.padding(top = 20.dp, bottom = 20.dp),
        title = {
            Column {
                Text(text = stringResource(id = R.string.app_name), style = MaterialTheme.typography.h6)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = stringResource(
                        id = R.string.home_subtitle
                    ),
                    color = Teal200,
                    style = MaterialTheme.typography.subtitle2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        navigationIcon = {
            Image(
                painter = painterResource(R.drawable.home_logo),
                contentDescription = "Home logo"
            )
        },
        actions = {
            HomeMenu()
        }
    )
}

@Preview
@Composable
fun HomeViewPreview() {
    HomeView()
}