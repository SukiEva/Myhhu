package github.sukieva.hhu.ui.activity.favourite

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import github.sukieva.hhu.data.local.MyAppDatabase
import github.sukieva.hhu.ui.activity.base.BaseActivity
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.MyWebView

class FavouriteActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val siteAddress: String? = intent.getStringExtra("siteAddress")
        val siteName: String? = intent.getStringExtra("siteName")
        setContent {
            FavouriteView(siteAddress,siteName)
        }
    }


}