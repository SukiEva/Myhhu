package top.sukiu.myhhu.service

import android.app.Service
import android.content.Intent
import android.os.*
import androidx.annotation.RequiresApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import top.sukiu.myhhu.util.AlarmManagerUtils
import top.sukiu.myhhu.util.LogUtil
import top.sukiu.myhhu.util.UserData
import top.sukiu.myhhu.util.notify

class ClockService : Service() {

    private val handler = object : Handler(Looper.getMainLooper()) {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                200 -> notify("每日健康上报", "打卡成功")
                300 -> notify("每日健康上报", "服务器连接超时")
                500 -> notify("每日健康上报", "打卡失败")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtil.d("ClockService", "Service Start")
        notify("每日健康打开", "开始")
        GlobalScope.launch {
            UserData.setData()
            UserData.clockIn(handler)
        }
        AlarmManagerUtils.getInstance(applicationContext).getUpAlarmManagerWorkOnOthers()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtil.d("ClockService", "Service Stopped")
    }
}