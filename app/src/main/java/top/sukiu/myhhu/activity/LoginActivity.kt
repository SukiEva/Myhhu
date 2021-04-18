package top.sukiu.myhhu.activity

import android.os.*
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.launch
import top.sukiu.myhhu.R
import top.sukiu.myhhu.bean.Grades
import top.sukiu.myhhu.util.*


class LoginActivity : AppCompatActivity() {

    val handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == 404) {
                toast("教务系统崩溃啦>_<")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)
        setSupportActionBar(login_bar)
        supportActionBar?.title = "Login"
        transportStatusBar(this, window, login_bar)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


        setInfo()

        LoginUtil.getCaptchaPic(checkCodePicture, handler)
        askGrades.setOnClickListener { loginButtonClickHandler() }
        refreshButton.setOnClickListener { refreshButtonClickHandler() }
    }

    private fun setInfo() {
        lifecycleScope.launch {
            loginNum.setText(readData("uname"))
            loginPassword.setText(readData("upswd"))
            if (readData("checkboxBoolean") == "true")
                rememberInfo.isChecked = true
        }
    }

    private fun loginButtonClickHandler() {
        val Num = loginNum.text.toString()
        val Password = loginPassword.text.toString()
        val Yzm = loginYzm.text.toString()
        when { // check if any is empty
            Num == "" -> {
                alert(this, message = "请填写学号！￣へ￣")
                return
            }
            Password == "" -> {
                alert(this, message = "请填写密码！(*￣︿￣)")
                return
            }
            Yzm == "" -> {
                alert(this, message = "请填写验证码！凸(艹皿艹 )")
                return
            }
        }
        lifecycleScope.launch {
            if (rememberInfo!!.isChecked) { // store user info by SharedPreferences
                addData("uname", Num)
                addData("upswd", Password)
                addData("checkboxBoolean", "true")
            } else {
                addData("uname", "")
                addData("upswd", "")
                addData("checkboxBoolean", "false")
            }
        }
        val homehtml = LoginUtil.login(Num, Password, Yzm, checkCodePicture, handler, loginYzm)
        homehtml?.let {
            if (it.contains("URP综合教务系统首页")) {
                val rank = LoginUtil.getRank()
                val courses = LoginUtil.getGrades()
                val grades = Grades(courses)
                start(
                    ShowResultsActivity::class.java,
                    dataSerializable = mapOf("grades" to grades, "rank" to rank)
                )
                finish()
                return
            }
        }
        toast("Login failed. Please try again!")
        loginYzm.setText("")
        LoginUtil.getCaptchaPic(checkCodePicture, handler)
    }

    private fun refreshButtonClickHandler() {
        val animation = RotateAnimation(
            0.0f, 360.0f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        animation.duration = 500
        refreshButton.startAnimation(animation)
        LoginUtil.getCaptchaPic(checkCodePicture, handler)
    }
}