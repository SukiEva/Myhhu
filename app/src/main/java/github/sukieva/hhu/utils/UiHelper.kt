package github.sukieva.hhu.utils

import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import github.sukieva.hhu.MyApp


fun String.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MyApp.context, this, duration).show()
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(MyApp.context, this, duration).show()
}

fun View.showSnackbar(text: String, duration: Int = Snackbar.LENGTH_SHORT) {
    // TODO: 2021/9/28  
}