package top.sukiu.myhhu.activity

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_schedule.*
import top.sukiu.myhhu.R
import top.sukiu.myhhu.util.LogUtil
import top.sukiu.myhhu.util.toast
import top.sukiu.myhhu.util.transportStatusBar


class ScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        setSupportActionBar(schedule_bar)
        supportActionBar?.title = "Schedule"
        transportStatusBar(this, window, schedule_bar)

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
            LogUtil.d("ScheduleActivity", "Schedule Error: not installed")
            e.printStackTrace()
        }
    }

}