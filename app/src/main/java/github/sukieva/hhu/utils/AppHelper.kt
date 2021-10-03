package github.sukieva.hhu.utils


import android.content.Intent
import android.net.Uri
import android.widget.Toast
import es.dmoral.toasty.Toasty
import github.sukieva.hhu.MyApp


fun browse(url: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    MyApp.context.startActivity(intent)
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