package github.sukieva.hhu.ui.activity.favourite

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.RestartAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import github.sukieva.hhu.MyApp
import github.sukieva.hhu.R
import github.sukieva.hhu.data.entity.Website
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.*
import github.sukieva.hhu.ui.theme.fontHead
import github.sukieva.hhu.utils.*


@Composable
fun FavouriteView(siteAddress: String? = null, siteName: String? = null) {
    val model: FavouriteViewModel = viewModel()
    if (siteAddress != null && siteName != null) {
        LogUtil.d("FavouriteActivity", "==>$siteAddress")
        InitView {
            MyScaffold(topBar = { FavWebAppBar(siteAddress, siteName) }) {
                MyWebView(siteAddress)
            }
        }
    } else {
        model.getWebs()
        val websites = model.websites
        InitView {
            MyScaffold(topBar = { FavAppBar() }) {
                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                    items(websites) { website ->
                        FavListItem(website)
                    }
                }
            }
        }
    }
}

@Composable
fun FavListItem(website: Website) {
    val model: FavouriteViewModel = viewModel()
    ListCard(
        title = website.siteName,
        modifier = Modifier
            .padding(start = 5.dp, end = 5.dp, top = 12.dp, bottom = 12.dp)
            .fillMaxWidth()
            .height(50.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        alert(
                            message = MyApp.context.getString(R.string.dialog_press_message),
                            positive = MyApp.context.getString(R.string.dialog_press_confirm),
                            showNegative = true,
                            negative = MyApp.context.getString(R.string.dialog_press_cancel),
                            onPositiveClick = {
                                model.deleteWebByName(website.siteName)
                                model.refreshUI()
                            }
                        )
                    },
                    onTap = {
                        start<FavouriteActivity> {
                            putExtra("siteAddress", website.siteAddress)
                            putExtra("siteName", website.siteName)
                        }
                    }
                )
            }
    )
}

@Composable
fun FavWebAppBar(siteAddress: String, siteName: String) {
    MaterialTopAppBar(
        title = siteName,
        actions = {
            IconButton(onClick = { browse(siteAddress) }) {
                Icon(Icons.Outlined.OpenInBrowser, contentDescription = "Browse by system")
            }
        }
    )
    if (siteName == "信息门户")
        "信息门户请在浏览器中打开哦".warningToast()
}


@Composable
fun FavAppBar() {
    val model: FavouriteViewModel = viewModel()
    val openDialog = remember { model.isOpenDialog }
    MaterialTopAppBar(
        title = stringResource(id = R.string.home_list_favourite),
        actions = {
            IconButton(onClick = {
                model.isOpenDialog.value = true
                openDialog.value = model.isOpenDialog.value
                LogUtil.d("FavAppBar", "Add a new website")
            }) {
                Icon(Icons.Rounded.Add, contentDescription = "Add a website")
            }
            IconButton(onClick = {
                MyApp.context.getString(R.string.appbar_reset).infoToast()
                model.resetWeb()
                model.refreshUI()
                LogUtil.d("FavAppBar", "Reset websites")
            }) {
                Icon(Icons.Rounded.RestartAlt, contentDescription = "Reset websites")
            }
        }
    )
    if (openDialog.value) AddWebDialog()
}

@Composable
fun AddWebDialog() {
    val model: FavouriteViewModel = viewModel()
    val webNameState = remember { model.webName }
    val webUrlState = remember { model.webAddress }
    AlertDialog(
        onDismissRequest = {
            model.isOpenDialog.value = false
            model.clearDialog()
        },
        text = {
            Column {
                Text(
                    text = stringResource(id = R.string.dialog_title),
                    color = MaterialTheme.colors.secondary,
                    fontSize = 20.sp
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )
                OutlinedTextField(
                    value = webNameState.value,
                    onValueChange = { model.webName.value = it },
                    label = { Text(stringResource(id = R.string.dialog_web_name)) },
                    textStyle = TextStyle(color = MaterialTheme.colors.fontHead),
                    maxLines = 1
                )
                OutlinedTextField(
                    value = webUrlState.value,
                    onValueChange = { model.webAddress.value = it },
                    label = { Text(stringResource(id = R.string.dialog_web_url)) },
                    textStyle = TextStyle(color = MaterialTheme.colors.fontHead),
                    placeholder = {
                        Text("https://baidu.com")
                    }
                )
            }
        },
        confirmButton = {
            IconButton(onClick = {
                if (model.checkNull() && model.checkUrl()) {
                    LogUtil.d("FavDialog", "Add Url ==> ${webUrlState.value}")
                    model.addWeb()
                    model.isOpenDialog.value = false
                    model.clearDialog()
                    model.refreshUI()
                }
            }) {
                Icon(
                    Icons.Rounded.Done,
                    contentDescription = "Confirm button",
                    tint = MaterialTheme.colors.secondary,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}

@Preview
@Composable
fun FavouriteViewPreview() {
    FavouriteView()
}