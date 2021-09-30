package github.sukieva.hhu.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ManageSearch
import androidx.compose.material.icons.rounded.PendingActions
import androidx.compose.ui.Modifier
import github.sukieva.hhu.ui.activity.base.BaseActivity
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.CardItem
import github.sukieva.hhu.ui.components.HomeAppBar
import github.sukieva.hhu.utils.showToast

class MainActivity : BaseActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InitView {
                Column(modifier = Modifier.fillMaxHeight()) {
                    HomeAppBar()
                    CardItem(
                        isLarge = true,
                        isActive = true,
                        onClick = { "点击了".showToast() },
                        title = "健康打卡",
                        body = "已经打卡"
                    )
                    CardItem(
                        title = "成绩查询",
                        body = "啦啦啦",
                        icon = Icons.Rounded.ManageSearch
                    )
                    CardItem(
                        title = "课程表",
                        body = "啦啦啦",
                        icon = Icons.Rounded.PendingActions
                    )

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        "开始交互".showToast()
    }


}

