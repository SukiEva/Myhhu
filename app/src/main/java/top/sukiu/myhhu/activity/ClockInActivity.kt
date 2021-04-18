package top.sukiu.myhhu.activity

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_clock_in.*
import kotlinx.coroutines.launch
import top.sukiu.myhhu.R
import top.sukiu.myhhu.util.*
import top.sukiu.myhhu.util.UserData.Address
import top.sukiu.myhhu.util.UserData.Building
import top.sukiu.myhhu.util.UserData.Classes
import top.sukiu.myhhu.util.UserData.Grade
import top.sukiu.myhhu.util.UserData.Institute
import top.sukiu.myhhu.util.UserData.Major
import top.sukiu.myhhu.util.UserData.Name
import top.sukiu.myhhu.util.UserData.PhoneNum
import top.sukiu.myhhu.util.UserData.Room
import top.sukiu.myhhu.util.UserData.SelfAccount
import top.sukiu.myhhu.util.UserData.account
import top.sukiu.myhhu.util.UserData.clockIn
import top.sukiu.myhhu.util.UserData.wid

class ClockInActivity : AppCompatActivity() {

    var flag: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock_in)
        setSupportActionBar(clock_in_bar)
        supportActionBar?.title = "Clock In"
        transportStatusBar(this, window, clock_in_bar)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        lifecycleScope.launch { UserData.setData() }
        checkDakaInfo()
        dakaButton.setOnClickListener { dakaButtonHandle() }
        setButton.setOnClickListener { start(ClockInSetActivity::class.java) }
        at_home.setOnCheckedChangeListener { _, _ -> atHomeButtonChange() }
        at_school.setOnCheckedChangeListener { _, _ -> atSchoolButtonChange() }
        //auto.setOnCheckedChangeListener { _, _ -> autoButtonChange() }

        if (UserData.Where == "home") {
            at_home.isChecked = true
            at_school.isChecked = false
        } else {
            at_home.isChecked = false
            at_school.isChecked = true
        }
    }

    private val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                100 -> toast("开始打卡，请耐心等待")
                200 -> toast("打卡成功")
                300 -> toast("服务器连接超时")
                500 -> toast("打卡失败")
            }
        }
    }

//    private fun autoButtonChange() {
//        when (auto.isChecked) {
//            true -> {
//                val calendar = Calendar.getInstance()
//                TimePickerDialog(
//                    this,
//                    { _, hourOfDay, minute ->
//                        run {
//                            val dueDate = Calendar.getInstance()
//                            dueDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
//                            dueDate.set(Calendar.MINUTE, minute)
//                            dueDate.set(Calendar.SECOND, 0)
//                            LogUtil.d(
//                                "ClockInActivity",
//                                "Set Time $hourOfDay $minute"
//                            )
//                        }
//                    },
//                    calendar.get(Calendar.HOUR_OF_DAY),
//                    calendar.get(Calendar.MINUTE),
//                    true
//                ).show()
//
//            }
//            false -> {
//
//            }
//        }
//    }


    private fun dakaButtonHandle() {
        if (!flag) {
            toast("请补全打卡信息")
            return
        }
        if (at_home.isChecked && Address == "") {
            toast("请填写地址信息")
            return
        }
        lifecycleScope.launch {
            if (at_home.isChecked) {
                addData("Where", "home")
                UserData.Where = "home"
            } else {
                addData("Where", "school")
                UserData.Where = "school"
            }
        }
        clockIn(handler)
    }


    private fun checkDakaInfo() {
        if (account == "" || wid == "" || Name == "" ||
            SelfAccount == "" || Institute == "" || Grade == "" || Major == "" ||
            Classes == "" || Building == "" || Room == "" || PhoneNum == ""
        ) {
            toast("请补全打卡信息")
            return
        }
        flag = true
    }

    private fun atHomeButtonChange() {
        if (at_school.isChecked && at_home.isChecked) at_school.isChecked = false
        if (!at_home.isChecked && !at_school.isChecked) at_school.isChecked = true
    }

    private fun atSchoolButtonChange() {
        if (at_home.isChecked && at_school.isChecked) at_home.isChecked = false
        if (!at_home.isChecked && !at_school.isChecked) at_home.isChecked = true
    }

}