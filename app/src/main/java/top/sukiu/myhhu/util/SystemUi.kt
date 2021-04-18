package top.sukiu.myhhu.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.Window
import android.widget.Toast
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
    Toast.makeText(MyApplication.context, message, time).show()
}

fun start(
    activity: Class<*>,
    dataSerializable: Map<String, Serializable>? = null,
    dataString: Map<String, String>? = null
) {
    val context = MyApplication.context
    val intent = Intent(context, activity)
    dataSerializable?.let {
        for ((key, value) in it)
            intent.putExtra(key, value)
    }
    dataString?.let {
        for ((key, value) in it)
            intent.putExtra(key, value)
    }
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}


