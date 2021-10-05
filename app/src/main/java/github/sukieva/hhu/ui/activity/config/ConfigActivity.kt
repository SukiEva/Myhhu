package github.sukieva.hhu.ui.activity.config

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import github.sukieva.hhu.ui.activity.base.BaseActivity

class ConfigActivity : BaseActivity() {
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ConfigView()
        }
    }
}