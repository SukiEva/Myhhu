package github.sukieva.hhu.utils

import android.widget.Toast
import es.dmoral.toasty.Toasty
import github.sukieva.hhu.MyApp


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













