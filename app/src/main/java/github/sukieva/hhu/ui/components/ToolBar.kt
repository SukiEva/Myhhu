package github.sukieva.hhu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import github.sukieva.hhu.R
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.theme.Teal200


@Composable
fun HomeAppBar() {
    MaterialAppBar(
        modifier = Modifier.padding(top = 20.dp,bottom = 20.dp),
        title = {
            Column {
                MaterialHead(text = stringResource(id = R.string.app_name))
                Spacer(modifier = Modifier.height(10.dp))
                MaterialSub(text = stringResource(id = R.string.home_subtitle), color = Teal200)
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

@Composable
fun MaterialAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.background,
        contentColor = MaterialTheme.colors.onSurface,
        elevation = 0.dp, // No shadow needed
        title = title,
        navigationIcon = navigationIcon,
        contentPadding = rememberInsetsPaddingValues(
            LocalWindowInsets.current.statusBars,
            applyStart = true,
            additionalStart = 10.dp,
            applyEnd = true,
            additionalEnd = 10.dp
        ),
        actions = actions
    )
}


@Preview
@Composable
fun AppBarPreview() {
    InitView {
        HomeAppBar()
    }
}
