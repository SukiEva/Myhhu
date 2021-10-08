package github.sukieva.hhu.utils


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import es.dmoral.toasty.Toasty
import github.sukieva.hhu.MyApp
import github.sukieva.hhu.R
import java.util.*


fun browse(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    MyApp.context.startActivity(intent)
}

fun alert(
    context: Context = ActivityCollector.topActivity(),
    title: String = "(/￣ー￣)/~~☆’.･.･:★’.･.･:☆",
    message: String = "消息",
    positive: String = "确认",
    onPositiveClick: () -> Unit = {},
    showNegative: Boolean = false,
    negative: String = "取消",
    onNegativeClick: () -> Unit = {}
) {
    AlertDialog.Builder(context, R.style.AlertDialogStyle).apply {
        setTitle(title)
        setMessage(message)
        setCancelable(false)
        setPositiveButton(positive) { _, _ ->
            onPositiveClick()
        }
        if (showNegative) {
            setNegativeButton(negative) { _, _ ->
                onNegativeClick()
            }
        }
        setCancelable(true)
        show()
    }
}

/* 范型实化
   start<ExampleActivity>(){
        putExtra()
        putExtra()
   }
 */
inline fun <reified T> start(block: Intent.() -> Unit = {}) {
    val context = MyApp.context
    val intent = Intent(context, T::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    intent.block()
    context.startActivity(intent)
}

fun String.showToast(duration: Int = Toast.LENGTH_SHORT) =
    Toasty.normal(MyApp.context, this, duration).show()


fun String.infoToast(duration: Int = Toast.LENGTH_SHORT) =
    Toasty.info(MyApp.context, this, duration, true).show()


fun String.successToast(duration: Int = Toast.LENGTH_SHORT) =
    Toasty.success(MyApp.context, this, duration, true).show()


fun String.warningToast(duration: Int = Toast.LENGTH_SHORT) =
    Toasty.warning(MyApp.context, this, duration, true).show()


fun String.errorToast(duration: Int = Toast.LENGTH_SHORT) =
    Toasty.error(MyApp.context, this, duration, true).show()


fun getDate(): String {
    val calendar: Calendar = Calendar.getInstance()
    val y = calendar.get(Calendar.YEAR).toString()
    var m = (calendar.get(Calendar.MONTH) + 1).toString()
    var d = calendar.get(Calendar.DAY_OF_MONTH).toString()
    if (m.toInt() < 10) m = "0$m"
    if (d.toInt() < 10) d = "0$d"
    return "$y/$m/$d"
}

fun notify(title: String? = "", body: String? = "") = NotifyUtil().notify(title, body)
