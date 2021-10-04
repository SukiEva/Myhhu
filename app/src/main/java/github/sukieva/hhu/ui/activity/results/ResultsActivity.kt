package github.sukieva.hhu.ui.activity.results

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import github.sukieva.hhu.ui.activity.base.BaseActivity
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.CaptchaPic
import github.sukieva.hhu.ui.components.MaterialTopAppBar

class ResultsActivity : BaseActivity() {

    private lateinit var viewModel: ResultsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ResultsViewModel::class.java)
        val isLogin = intent.getBooleanExtra("isLogin", false)

        setContent {
            ResultsView()
        }


    }


}