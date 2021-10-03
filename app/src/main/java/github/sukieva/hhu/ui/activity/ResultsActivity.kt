package github.sukieva.hhu.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import github.sukieva.hhu.network.EasyOkhttp
import github.sukieva.hhu.ui.activity.base.BaseActivity
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.CaptchaPic
import github.sukieva.hhu.ui.components.MaterialTopAppBar
import github.sukieva.hhu.ui.viewmodel.ResultsViewModel

class ResultsActivity : BaseActivity() {

    private lateinit var viewModel: ResultsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ResultsViewModel::class.java)

        setContent {
            InitView {
                MaterialTopAppBar()
                CaptchaPic()
            }
        }
        viewModel.getCaptchaPic()

        val isLogin = intent.getBooleanExtra("isLogin", false)

    }


}