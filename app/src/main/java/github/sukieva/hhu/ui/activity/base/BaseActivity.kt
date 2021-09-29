package github.sukieva.hhu.ui.activity.base

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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

    @Composable
    private fun TransparentSystemBar() {
        val systemUiController = rememberSystemUiController()
        val useDarkIcons = !isSystemInDarkTheme()
        SideEffect {
            systemUiController.setSystemBarsColor(
                color = Color.Transparent,
                darkIcons = useDarkIcons
            )
        }
    }

    @Composable
    open fun InitView(content: @Composable () -> Unit) {
        TransparentSystemBar()
        MyTheme {
            Surface(color = MaterialTheme.colors.background) {
                content()
            }
        }
    }
}