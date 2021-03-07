package top.sukiu.myhhu.activity

import android.app.Instrumentation
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_show_rank.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import top.sukiu.myhhu.R
import top.sukiu.myhhu.bean.Rank


class ShowRankActivity : AppCompatActivity() {

    val log = AnkoLogger<ShowRankActivity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransportStatusBar()
        setContentView(R.layout.activity_show_rank)
        setSupportActionBar(rank_bar)
        supportActionBar?.title = "Rank"

        setRankList()
    }

    private fun setRankList() {
        val info = intent.getSerializableExtra("rank") as Rank
        info.let {
            major.text = it.Major
            majornum.text = it.MajorNum.toString()
            avagrade.text = it.AvarageGrade.toString()
            grade.text = it.Grade.toString()
            avagpa.text = it.AvarageGradePoint.toString()
            gpa.text = it.GradePoint.toString()
            avarank.text = it.AvarageRank.toString()
            rank.text = it.Rank.toString()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBack()
            //finish()
        }
        return super.onOptionsItemSelected(item)
    }

    fun onBack() {
        object : Thread() {
            override fun run() {
                try {
                    val inst = Instrumentation()
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK)
                } catch (e: Exception) {
                    log.debug { "Exception when onBack" + e.stackTrace }
                    e.printStackTrace()
                }
            }
        }.start()
    }

    @Suppress("DEPRECATION")
    private fun TransportStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(Color.TRANSPARENT)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}