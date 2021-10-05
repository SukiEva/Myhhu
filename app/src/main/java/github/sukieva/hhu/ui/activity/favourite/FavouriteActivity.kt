package github.sukieva.hhu.ui.activity.favourite

import android.os.Bundle
import androidx.activity.compose.setContent
import github.sukieva.hhu.ui.activity.base.BaseActivity

class FavouriteActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val siteAddress: String? = intent.getStringExtra("siteAddress")
        val siteName: String? = intent.getStringExtra("siteName")
        setContent {
            FavouriteView(siteAddress, siteName)
        }
    }


}