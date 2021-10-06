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
import github.sukieva.hhu.ui.activity.about.AboutActivity
import github.sukieva.hhu.ui.theme.fontBody
import github.sukieva.hhu.utils.ActivityCollector
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
            DropdownMenuItem(onClick = {
                ActivityCollector.finishAllActivity()
                expanded = false
            }) {
                MaterialFont(stringResource(id = R.string.home_menu_exit))
            }
            Divider()
            DropdownMenuItem(onClick = {
                browse("https://github.com/SukiEva/Myhhu/issues")
                expanded = false
            }) {
                MaterialFont(stringResource(id = R.string.home_menu_issue))
            }
            DropdownMenuItem(onClick = {
                start<AboutActivity>()
                expanded = false
            }) {
                MaterialFont(stringResource(id = R.string.home_menu_about))
            }
        }
    }
}

@Composable
fun MaterialFont(
    text: String,
) {
    Text(
        text = text,
        color = MaterialTheme.colors.fontBody,
        style = MaterialTheme.typography.body1
    )
}

@Preview
@Composable
fun HomeMenuPreview() {
    HomeMenu()
}