package github.sukieva.hhu.ui.activity.results

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import github.sukieva.hhu.ui.activity.base.BaseActivity

class ResultsActivity : BaseActivity() {

    lateinit var viewModel: ResultsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ResultsViewModel::class.java)
        viewModel.isLogin = intent.getBooleanExtra("isLogin", false)
        setContent {
            ResultsView()
        }

    }


}