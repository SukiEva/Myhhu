package github.sukieva.hhu.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.ui.Modifier
import github.sukieva.hhu.ui.activity.base.BaseActivity
import github.sukieva.hhu.ui.components.Counter
import github.sukieva.hhu.ui.components.HomeToolBar
import github.sukieva.hhu.ui.components.MessageCard
import github.sukieva.hhu.utils.showToast

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InitView {
                Column(modifier = Modifier.fillMaxHeight()) {
                    HomeToolBar("我说标题")
                    MessageCard()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        "开始交互".showToast()
    }



}

