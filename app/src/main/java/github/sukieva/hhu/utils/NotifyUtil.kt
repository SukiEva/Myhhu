package github.sukieva.hhu.utils

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContextWrapper
import android.content.Intent
import androidx.core.app.NotificationCompat
import github.sukieva.hhu.MyApp
import github.sukieva.hhu.R
import github.sukieva.hhu.ui.activity.home.HomeActivity


class NotifyUtil : ContextWrapper(MyApp.context) {

    init {
        val channel = NotificationChannel(PRIMARY_CHANNEL_ID, getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        getManager()!!.createNotificationChannel(channel)
    }

    private var manager: NotificationManager? = null

    private fun getManager(): NotificationManager? {
        if (manager == null) {
            manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager!!.cancel(1)
        }
        return manager
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setIntent(): PendingIntent? {
        val intent = Intent(this, HomeActivity::class.java)
        return PendingIntent.getActivity(this, 0, intent, 0)
    }

    private fun getNotification(title: String?, body: String?): Notification =
        NotificationCompat.Builder(MyApp.context, PRIMARY_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setAutoCancel(true)
            .setOnlyAlertOnce(true)
            .setContentIntent(setIntent())
            .build()


    fun notify(title: String?, body: String?) {
        val notification = getNotification(title, body)
        getManager()!!.notify(1, notification)
    }


    companion object {
        const val PRIMARY_CHANNEL_ID = "打卡通知"
    }
}