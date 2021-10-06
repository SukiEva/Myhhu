package github.sukieva.hhu.ui.activity.home

import android.os.Bundle
import androidx.activity.compose.setContent
import github.sukieva.hhu.ui.activity.base.BaseActivity
import github.sukieva.hhu.utils.errorToast

class HomeActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeView()
        }
    }



}

