package top.sukiu.myhhu.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about.*
import top.sukiu.myhhu.R
import top.sukiu.myhhu.util.LogUtil
import top.sukiu.myhhu.util.browse
import top.sukiu.myhhu.util.transportStatusBar


class AboutActivity : AppCompatActivity() {

    private val TAG = "AboutActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(about_bar)
        supportActionBar?.title = "About"
        transportStatusBar(this, window, about_bar, true)

        problem.setOnClickListener {
            browse("https://github.com/SukiEva/Myhhu/issues")
            LogUtil.i(TAG, " -> https://github.com/SukiEva/Myhhu/issues")
        }
        sourcecode.setOnClickListener {
            browse("https://github.com/SukiEva/Myhhu")
            LogUtil.i(TAG, " -> https://github.com/SukiEva/Myhhu")
        }
    }


}