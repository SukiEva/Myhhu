package github.sukieva.hhu.ui.activity.favourite

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.OpenInBrowser
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.RestartAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import github.sukieva.hhu.MyApp
import github.sukieva.hhu.R
import github.sukieva.hhu.data.entity.Website
import github.sukieva.hhu.data.repository.LocalRepository
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.MaterialTopAppBar
import github.sukieva.hhu.ui.components.MyListItem
import github.sukieva.hhu.ui.components.MyWebView
import github.sukieva.hhu.utils.*
import kotlinx.coroutines.launch


@Composable
fun FavouriteView(siteAddress: String? = null, siteName: String? = null) {
    if (siteAddress != null && siteName != null) {
        LogUtil.d("FavouriteActivity", "==>$siteAddress")
        InitView {
            FavWebAppBar(siteAddress, siteName)
            MyWebView(siteAddress)
        }
    } else {
        val flow = LocalRepository.getWebsites()
        val websites by flow.collectAsState(initial = emptyList())
        InitView {
            FavAppBar()
            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                items(websites) { website ->
                    FavListItem(website)
                }
            }
        }
    }
}

@Composable
fun FavListItem(website: Website) {
    MyListItem(
        title = website.siteName,
        onClick = {
            start<FavouriteActivity> {
                putExtra("siteAddress", website.siteAddress)
                putExtra("siteName", website.siteName)
            }
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
    val composableScope = rememberCoroutineScope()
    MaterialTopAppBar(
        title = stringResource(id = R.string.home_list_favourite),
        actions = {
            IconButton(onClick = {
                composableScope.launch {
                    LocalRepository.insertWeb(Website("百度", "https://baidu.com"))
                    start<FavouriteActivity>()
                }
                ActivityCollector.finishTopActivity()
                LogUtil.d("FavAppBar", "Add a new website")
            }) {
                Icon(Icons.Rounded.Add, contentDescription = "Add a website")
            }
            IconButton(onClick = {
                MyApp.context.getString(R.string.appbar_reset).infoToast()
                composableScope.launch {
                    LocalRepository.resetWebs()
                    start<FavouriteActivity>()
                }
                ActivityCollector.finishTopActivity()
                LogUtil.d("FavAppBar", "Reset websites")
            }) {
                Icon(Icons.Rounded.RestartAlt, contentDescription = "Reset websites")
            }
        }
    )
}

@Preview
@Composable
fun FavouriteViewPreview() {
    FavouriteView()
}