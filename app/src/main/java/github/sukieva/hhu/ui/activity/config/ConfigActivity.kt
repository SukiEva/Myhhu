package github.sukieva.hhu.ui.activity.config

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.lifecycle.ViewModelProvider
import github.sukieva.hhu.ui.activity.base.BaseActivity

class ConfigActivity : BaseActivity() {

    lateinit var viewModel: ConfigViewModel

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ConfigViewModel::class.java)
        viewModel.isFetchData = intent.getBooleanExtra("fetchData", false)
        setContent {
            ConfigView()
        }
    }
}