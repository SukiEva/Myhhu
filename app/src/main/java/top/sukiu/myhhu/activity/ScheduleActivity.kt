package top.sukiu.myhhu.activity

import android.content.ComponentName
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_schedule.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import top.sukiu.myhhu.R


class ScheduleActivity : AppCompatActivity() {

    val log = AnkoLogger<ScheduleActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransportStatusBar()
        setContentView(R.layout.activity_schedule)
        setSupportActionBar(schedule_bar)
        supportActionBar?.title = "Schedule"

        judgeApk()
    }

    private fun judgeApk() {
        val componentName = ComponentName(
            "com.suda.yzune.wakeupschedule",
            "com.suda.yzune.wakeupschedule.SplashActivity"
        )
        try {
            intent.component = componentName
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        } catch (e: Exception) {
            toast("Wakeup not installed!")
            log.info { "Schedule Error: not installed" }
            e.printStackTrace()
        }
    }
    @Suppress("DEPRECATION")
    private fun TransportStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(Color.TRANSPARENT)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}