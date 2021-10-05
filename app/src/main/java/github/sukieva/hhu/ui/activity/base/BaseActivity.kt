package github.sukieva.hhu.ui.activity.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import github.sukieva.hhu.ui.theme.MyTheme
import github.sukieva.hhu.utils.ActivityCollector


open class BaseActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

}

@Composable
fun InitView(content: @Composable () -> Unit) {

    MyTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }

}