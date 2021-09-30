package github.sukieva.hhu.utils

import android.content.Intent
import android.net.Uri
import github.sukieva.hhu.MyApp

fun browse(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    //LogUtil.i("Start URL", intent.data.toString())
    MyApp.context.startActivity(intent)
}

fun start(
    activity: Class<*>,
    data: Map<String, Any>? = null
) {
    val context = MyApp.context
    val intent = Intent(context, activity)
    data?.let {
        for ((key, value) in it) {
            when (value) {
                is String -> intent.putExtra(key, value)
                is Int -> intent.putExtra(key, value)
            }
        }
    }
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
}