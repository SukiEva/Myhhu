package top.sukiu.myhhu.activity

import android.app.TimePickerDialog
import android.os.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import kotlinx.android.synthetic.main.activity_clock_in.*
import top.sukiu.myhhu.R
import top.sukiu.myhhu.service.ClockWorker
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
import java.util.concurrent.TimeUnit


class ClockInActivity : AppCompatActivity() {


    private val TAG = "ClockInActivity"

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
        notify.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) setSP("notify", "true")
            else setSP("notify", "false")
        }
        if (sp("notify") == "true")
            notify.isChecked = true
        if (sp("AtHome", bool = true) == true) {
            at_home.isChecked = true
            at_school.isChecked = false
        } else {
            at_home.isChecked = false
            at_school.isChecked = true
        }
        auto.isChecked = sp("auto") == "true"
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
                if (sp("auto") == "true") return
                val calendar = Calendar.getInstance()
                TimePickerDialog(
                    this,
                    { _, hourOfDay, _ ->
                        run {
                            setSP("auto", "true")
                            setSP("targetHour", hourOfDay)
                            alarm(hourOfDay)
                            toast("定时设置成功")
                            LogUtil.i(
                                TAG,
                                "Set Time $hourOfDay Let's Start"
                            )
                        }
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()
            }
            false -> {
                setSP("auto", "false")
                WorkManager.getInstance(this).cancelUniqueWork("Clock")
                toast("定时设置已取消")
                LogUtil.i(
                    TAG,
                    "Cancel Worker"
                )
            }
        }
    }

    private fun alarm(hour: Int) {
        val request = PeriodicWorkRequestBuilder<ClockWorker>(15, TimeUnit.MINUTES)
            .addTag("Clock")
            .setInputData(workDataOf("target" to hour))
            .build()
        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork("Clock", ExistingPeriodicWorkPolicy.KEEP, request)
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
        if (at_home.isChecked)
            setSP("AtHome", true)
        else
            setSP("AtHome", false)
        clockIn(handler, at_home.isChecked)
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