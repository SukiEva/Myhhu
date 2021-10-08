package github.sukieva.hhu.ui.activity.results

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModelProvider
import com.google.accompanist.pager.ExperimentalPagerApi
import github.sukieva.hhu.ui.activity.base.BaseActivity
import github.sukieva.hhu.utils.ActivityCollector

class ResultsActivity : BaseActivity() {

    lateinit var viewModel: ResultsViewModel

    @ExperimentalPagerApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ResultsViewModel::class.java)
        viewModel.isLogin = intent.getBooleanExtra("isLogin", false)
        setContent {
            ResultsView()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCollector.finishTopActivity()
    }

}