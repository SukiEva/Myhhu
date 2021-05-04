package top.sukiu.myhhu.util

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.view.Gravity
import android.view.Window
import android.widget.EditText
import android.widget.LinearLayout
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
    StatusBarUtil.darkMode(activity, isDark)
    StatusBarUtil.darkMode(
        window,
        if (isDark) Color.BLACK else Color.WHITE,
        1f
    )
    StatusBarUtil.setPaddingSmart(MyApplication.context, toolbar)
}

fun isDarkTheme(): Boolean {
    return MyApplication.context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
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
    val notificationHelper = NotificationUtil(MyApplication.context)
    val mBuilder = notificationHelper.getNotificationPrivate(title, body)
    mBuilder.setOnlyAlertOnce(true)
    mBuilder.setSmallIcon(R.drawable.launch_round)
    //mBuilder.setProgress(0,0,false)
    notificationHelper.notify(1)
}

val sp: SharedPreferences = MyApplication.context.getSharedPreferences("Clock", MODE_PRIVATE)
fun setSP(key: String, value: Any) {
    val editor = sp.edit()
    when (value) {
        is String -> editor.putString(key, value)
        is Boolean -> editor.putBoolean(key, value)
    }
    editor.apply()
}

fun setSPSet(key: String, value: Set<String>?) {
    val editor = sp.edit()
    editor.putStringSet(key, value)
    editor.apply()
}

fun getSPSet(key: String): MutableSet<String>? {
    return sp.getStringSet(key, null)
}

fun sp(key: String, bool: Boolean = false): Any? {
    return if (bool) sp.getBoolean(key, false)
    else sp.getString(key, "")
}

fun removeSP(key: String) {
    val editor = sp.edit()
    editor.remove(key)
    editor.apply()
}

fun alertEdit(context: Context) {
    val liner = LinearLayout(context)
    liner.orientation = LinearLayout.VERTICAL
    liner.gravity = Gravity.CENTER
    val et1 = EditText(context)
    et1.hint = "名称"
    liner.addView(et1)
    val et2 = EditText(context)
    et2.hint = "网址，如 ：https://baidu.com"
    liner.addView(et2)
    AlertDialog.Builder(context).setTitle("请输入信息:）")
        .setIcon(R.drawable.home_logo)
        .setView(liner)
        .setPositiveButton("确定") { _, _ ->
            val name = et1.text.toString()
            val url = et2.text.toString()
            if (name == "" || url == "") {
                toast("不能为空")
            } else {
                val stringSet = getSPSet("WEB")
                stringSet?.add(name)
                setSPSet("WEB", stringSet)
                setSP(name, url)
                toast("保存成功")
            }
        }
        .setNegativeButton("取消", null)
        .show()
}
