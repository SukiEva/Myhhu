package github.sukieva.hhu.ui.activity.results

import android.os.Bundle
import androidx.activity.compose.setContent
import github.sukieva.hhu.ui.activity.base.BaseActivity

class ResultsActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isLogin = intent.getBooleanExtra("isLogin", false)

        setContent {
            ResultsView()
        }


    }


}