package github.sukieva.hhu.ui.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import github.sukieva.hhu.ui.activity.base.BaseActivity
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.*
import github.sukieva.hhu.utils.errorToast

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InitView {
                HomeAppBar()
                CardCheckIn()
                CardGradeQuery()
                CardSchedule()
                ListCardFavourite()
                ListCardConfig()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        "开始交互".errorToast()
    }


}

