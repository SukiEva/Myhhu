package top.sukiu.myhhu.activity

import android.app.Instrumentation
import android.os.Bundle
import android.view.KeyEvent
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_show_rank.*
import top.sukiu.myhhu.R
import top.sukiu.myhhu.bean.Rank
import top.sukiu.myhhu.util.LogUtil
import top.sukiu.myhhu.util.transportStatusBar


class ShowRankActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_show_rank)
        setSupportActionBar(rank_bar)
        supportActionBar?.title = "Rank"
        transportStatusBar(this, window, rank_bar)
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

    private fun onBack() {
        object : Thread() {
            override fun run() {
                try {
                    val inst = Instrumentation()
                    inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK)
                } catch (e: Exception) {
                    LogUtil.d("ShowRankActivity", "Exception when onBack" + e.stackTrace)
                    e.printStackTrace()
                }
            }
        }.start()
    }

}