package github.sukieva.hhu.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.LocalWindowInsets
import com.google.accompanist.insets.rememberInsetsPaddingValues
import com.google.accompanist.insets.ui.TopAppBar
import github.sukieva.hhu.utils.ActivityCollector


@Composable
fun MaterialTopAppBar(
    title: String = "Title",
    navigationIcon: @Composable () -> Unit = {
        IconButton(onClick = { ActivityCollector.finishTopActivity() }) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "Arrow back")
        }
    },
    actions: @Composable RowScope.() -> Unit = {}
) {
    MaterialAppBar(
        title = {
            Text(text = title, style = MaterialTheme.typography.h6)
        },
        navigationIcon = navigationIcon,
        actions = actions
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
        backgroundColor = Color.Transparent,
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
fun MaterialTopAppBarPreview() {
    MaterialTopAppBar()
}

@Preview
@Composable
fun MaterialAppBarPreview() {
    MaterialAppBar()
}