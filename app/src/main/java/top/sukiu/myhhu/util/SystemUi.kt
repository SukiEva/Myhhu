package top.sukiu.myhhu.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.view.Window
import android.widget.Toast
import androidx.annotation.RequiresApi
import top.sukiu.myhhu.R
import java.io.Serializable

fun transportStatusBar(
    activity: Activity,
    window: Window,
    toolbar: androidx.appcompat.widget.Toolbar,
    isDark: Boolean = false
) {
    StatusBarUtil.immersive(window)
    StatusBarUtil.darkMode(activity, false)
    StatusBarUtil.darkMode(
        window,
        if (isDark) Color.BLACK else Color.WHITE,
        1f
    )
    StatusBarUtil.setPaddingSmart(MyApplication.context, toolbar)
}


fun browse(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    MyApplication.context.startActivity(intent)
}

fun alert(context: Context, title: String = "可恶___*( ￣皿￣)/#____", message: String) {
    AlertDialog.Builder(context).apply {
        setTitle(title)
        setMessage(message)
        setCancelable(false)
        setPositiveButton("(ρ_・).。 OK") { _, _ -> }
        show()
    }
}

fun toast(message: String, time: Int = Toast.LENGTH_SHORT) {
    val toast = Toast.makeText(MyApplication.context, message, time)
    toast.show()
}

fun start(
    activity: Class<*>,
    data: Map<String, Any>? = null
) {
    val context = MyApplication.context
    val intent = Intent(context, activity)
    data?.let {
        for ((key, value) in it) {
            when (value) {
                is String -> intent.putExtra(key, value)
                is Serializable -> intent.putExtra(key, value)
                is Int -> intent.putExtra(key, value)
            }
        }
    }
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}

@RequiresApi(api = Build.VERSION_CODES.O)
fun notify(title: String? = "", body: String? = "") {
    val notificationHelper = NotificationHelper(MyApplication.context)
    val mBuilder = notificationHelper.getNotificationPrivate(title, body)
    mBuilder.setOnlyAlertOnce(true)
    mBuilder.setSmallIcon(R.drawable.launch_round)
    //mBuilder.setProgress(0,0,false)
    notificationHelper.notify(1)
}

