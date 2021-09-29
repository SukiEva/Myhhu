package github.sukieva.hhu.ui.components

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import github.sukieva.hhu.ui.theme.MyTheme


@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "light"
)
@Preview(
    showBackground = true,
    //showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "dark"
)
@Composable
fun LightPreview() {
    InitView {
        Column(modifier = Modifier.fillMaxHeight()) {
            HomeToolBar("我说标题")
            MessageCard()
        }
    }
}


@Composable
fun Greeting(name: String) {
    var isSelected by remember { mutableStateOf(false) }
    val backgroundColor by animateColorAsState(if (isSelected) Color.Red else Color.Transparent)

    Text(
        text = "Hello $name!",
        modifier = Modifier
            .padding(24.dp)
            .background(color = backgroundColor)
            .clickable(onClick = { isSelected = !isSelected })
    )
}

@Composable
fun Counter(count: Int, updateCount: (Int) -> Unit) {
    Button(
        onClick = { updateCount(count + 1) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (count > 5) Color.Green else Color.White
        )
    ) {
        Text("I've been clicked $count times")
    }
}


@Composable
fun InitView(content: @Composable () -> Unit) {
    TransparentSystemBar()
    MyTheme {
        Surface(color = MaterialTheme.colors.background) {
            content()
        }
    }
}


@Composable
fun TransparentSystemBar() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }
}
