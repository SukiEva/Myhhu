package github.sukieva.hhu.utils


import android.content.Intent
import android.net.Uri
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