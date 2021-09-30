package github.sukieva.hhu.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import github.sukieva.hhu.R
import github.sukieva.hhu.ui.activity.AboutActivity
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.utils.browse
import github.sukieva.hhu.utils.start


@Composable
fun HomeMenu() {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.wrapContentSize(Alignment.TopStart)) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "More information")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = { /* Handle refresh! */ }) {
                MaterialBody(stringResource(id = R.string.home_menu_add))
            }
            Divider()
            DropdownMenuItem(onClick = { browse("https://github.com/SukiEva/Myhhu/issues") }) {
                MaterialBody(stringResource(id = R.string.home_menu_issue))
            }
            DropdownMenuItem(onClick = { start(AboutActivity::class.java) }) {
                MaterialBody(stringResource(id = R.string.home_menu_about))
            }
        }
    }
}

@Preview
@Composable
fun HomeMenuPreview() {
    InitView {
        HomeMenu()
    }
}