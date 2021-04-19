package top.sukiu.myhhu.activity

import android.app.TimePickerDialog
import android.content.Intent
import android.os.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_clock_in.*
import kotlinx.coroutines.launch
import top.sukiu.myhhu.R
import top.sukiu.myhhu.service.ClockService
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
import java.util.*


class ClockInActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clock_in)
        setSupportActionBar(clock_in_bar)
        supportActionBar?.title = "Clock In"
        transportStatusBar(this, window, clock_in_bar)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        lifecycleScope.launchWhenCreated { UserData.setData() }

        dakaButton.setOnClickListener { dakaButtonHandle() }
        setButton.setOnClickListener { start(ClockInSetActivity::class.java) }
        at_home.setOnCheckedChangeListener { _, _ -> atHomeButtonChange() }
        at_school.setOnCheckedChangeListener { _, _ -> atSchoolButtonChange() }
        auto.setOnCheckedChangeListener { _, _ -> autoButtonChange() }
        helpB.setOnClickListener { alert(this, message = "高版本安卓不保证成功，请保证后台运行") }
        if (UserData.Where == "home") {
            at_home.isChecked = true
            at_school.isChecked = false
        } else {
            at_home.isChecked = false
            at_school.isChecked = true
        }
        if (UserData.auto == "true") {
            alarm(UserData.hour, UserData.minute)
            auto.isChecked = true
        } else auto.isChecked = false
    }


    private val handler = object : Handler(Looper.getMainLooper()) {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                100 -> toast("开始打卡，请耐心等待")
                200 -> {
                    toast("打卡成功")
                    if (notify.isChecked)
                        notify("每日健康上报", "打卡成功")
                }
                300 -> {
                    toast("服务器连接超时")
                    if (notify.isChecked)
                        notify("每日健康上报", "服务器连接超时")
                }
                500 -> {
                    toast("打卡失败")
                    if (notify.isChecked)
                        notify("每日健康上报", "打卡失败")
                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun autoButtonChange() {
        when (auto.isChecked) {
            true -> {
                val calendar = Calendar.getInstance()
                TimePickerDialog(
                    this,
                    { _, hourOfDay, minute ->
                        run {
                            lifecycleScope.launch {
                                addData("auto", "true")
                                addData("hour", hourOfDay.toString())
                                addData("minute", minute.toString())
                            }
                            alarm(hourOfDay, minute)
                            toast("定时设置成功")
                        }
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            }
            false -> {
                lifecycleScope.launch { addData("auto", "false") }
                val intent = Intent(this, ClockService::class.java)
                stopService(intent)
            }
        }
    }

    private fun alarm(hourOfDay: Int, minute: Int) {
        val alarmManagerUtils = AlarmManagerUtils.getInstance(this)
        alarmManagerUtils.createGetUpAlarmManager()
        alarmManagerUtils.getUpAlarmManagerStartWork(hourOfDay, minute)
        LogUtil.i(
            "ClockInActivity",
            "Set Time $hourOfDay $minute"
        )
    }


    private fun dakaButtonHandle() {
        if (!checkDakaInfo()) {
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


    private fun checkDakaInfo(): Boolean {
        if (account == "" || wid == "" || Name == "" ||
            SelfAccount == "" || Institute == "" || Grade == "" || Major == "" ||
            Classes == "" || Building == "" || Room == "" || PhoneNum == ""
        )
            return false
        return true
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