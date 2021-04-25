package top.sukiu.myhhu.service

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import top.sukiu.myhhu.util.*
import java.util.*

class ClockWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val FLAG = "ClOCKPUSH"
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
    override fun doWork(): Result {
        val targetHour =
            inputData.getInt("target", 12)
        //val targetHour = target.toInt()
        val isPush = sp(FLAG)
        if (compareCurrentHour(targetHour)) {
            if (isPush == "false" || isPush == "")
                setSP(FLAG, "true")
            else return Result.retry()
        } else {
            setSP(FLAG, "false")
            LogUtil.d("Worker", "Not At The TargetHour")
            return Result.retry()
        }
        LogUtil.d("Worker", "Service Start")
        notify("每日健康打开", "开始")
        GlobalScope.launch {
            UserData.setData()
            UserData.clockIn(handler)
        }
        return Result.success()
    }

    private fun compareCurrentHour(targetHour: Int): Boolean {
        val current = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        LogUtil.d("Worker", "Now is $current , and Target is $targetHour")
        return current == targetHour
    }

}