package top.sukiu.myhhu.activity

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_clock_in_set.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.startActivity
import top.sukiu.myhhu.R

class ClockInSetActivity : AppCompatActivity() {

    var sp: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TransportStatusBar()
        setContentView(R.layout.activity_clock_in_set)
        setSupportActionBar(clock_in_set_bar)
        supportActionBar?.title = "Setting"

        sp = this.getSharedPreferences("dakainfo", Context.MODE_PRIVATE)

        ShowMessage()
        SaveButton.setOnClickListener { SaveButtonHandle() }
        wid_teach.setOnClickListener { browse("https://blog.csdn.net/qq_43640009/article/details/107868713") }
        CatchButton.setOnClickListener {
            startActivity<CatchInfoActivity>()
            finish()
        }
    }

    private fun ShowMessage() {
        sp!!.let {
            account.setText(it.getString("account", "").toString())
            wid.setText(it.getString("wid", "").toString())
            Name.setText(it.getString("Name", "").toString())
            SelfAccount.setText(it.getString("SelfAccount", "").toString())
            Institute.setText(it.getString("Institute", "").toString())
            Grade.setText(it.getString("Grade", "").toString())
            Major.setText(it.getString("Major", "").toString())
            Classes.setText(it.getString("Classes", "").toString())
            Building.setText(it.getString("Building", "").toString())
            Room.setText(it.getString("Room", "").toString())
            PhoneNum.setText(it.getString("PhoneNum", "").toString())
            Address.setText(it.getString("Address", "").toString())
        }
    }

    private fun SaveButtonHandle() {
        val editor = sp!!.edit()
        editor.putString("account", account.text.toString())
        editor.putString("wid", wid.text.toString())
        editor.putString("Name", Name.text.toString())
        editor.putString("SelfAccount", SelfAccount.text.toString())
        editor.putString("Institute", Institute.text.toString())
        editor.putString("Grade", Grade.text.toString())
        editor.putString("Major", Major.text.toString())
        editor.putString("Classes", Classes.text.toString())
        editor.putString("Building", Building.text.toString())
        editor.putString("Room", Room.text.toString())
        editor.putString("PhoneNum", PhoneNum.text.toString())
        editor.putString("Address", Address.text.toString())
        editor.commit()
        startActivity<ClockInActivity>()
    }


    @Suppress("DEPRECATION")
    private fun TransportStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.setStatusBarColor(Color.TRANSPARENT)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}