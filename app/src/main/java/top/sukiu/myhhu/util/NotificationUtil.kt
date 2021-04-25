package top.sukiu.myhhu.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import top.sukiu.myhhu.R

class NotificationUtil @RequiresApi(api = Build.VERSION_CODES.O) constructor(ctx: Context?) :
    ContextWrapper(ctx) {
    private var manager: NotificationManager? = null
    private var mBuilder: Notification.Builder? = null

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun getNotificationPublic(title: String?, body: String?): Notification.Builder {
        return Notification.Builder(applicationContext, SECONDARY_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(smallIcon)
            .setAutoCancel(true).also { mBuilder = it }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    fun getNotificationPrivate(title: String?, body: String?): Notification.Builder {
        return Notification.Builder(applicationContext, PRIMARY_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(smallIcon)
            .setAutoCancel(true).also { mBuilder = it }
    }

    fun notify(id: Int, notification: Notification.Builder) {
        getManager()!!.notify(id, notification.build())
    }

    fun notify(id: Int) {
        getManager()!!.notify(id, mBuilder!!.build())
    }

    private val smallIcon: Int
        private get() = R.drawable.launch_round

    private fun getManager(): NotificationManager? {
        if (manager == null) {
            manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager!!.cancel(1)
        }
        return manager
    }

    companion object {
        const val PRIMARY_CHANNEL_ID = "打卡通知"
        const val SECONDARY_CHANNEL_ID = "自动打卡通知"
    }

    init {
        val chan1 = NotificationChannel(
            PRIMARY_CHANNEL_ID,
            getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT
        )
        chan1.lightColor = Color.GREEN
        chan1.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        getManager()!!.createNotificationChannel(chan1)
        val chan2 = NotificationChannel(
            SECONDARY_CHANNEL_ID,
            getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH
        )
        chan2.lightColor = Color.BLUE
        chan2.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        getManager()!!.createNotificationChannel(chan2)
    }
}