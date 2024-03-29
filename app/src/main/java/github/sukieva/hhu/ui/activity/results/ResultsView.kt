package github.sukieva.hhu.ui.activity.results

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import github.sukieva.hhu.R
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.GradesScreen
import github.sukieva.hhu.ui.components.MaterialTopAppBar
import github.sukieva.hhu.ui.components.MyScaffold
import github.sukieva.hhu.ui.components.RankScreen
import github.sukieva.hhu.ui.theme.fontHead
import github.sukieva.hhu.utils.ActivityCollector


@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun ResultsView() {
    val model: ResultsViewModel = viewModel()
    if (!model.isLogin) {
        InitView {
            MyScaffold(topBar = { MaterialTopAppBar(title = stringResource(id = R.string.results_title)) }) {
                LoginDialog()
            }
        }
    } else {
        model.getResults()
        val navController = rememberNavController()
        NavHost(navController, startDestination = "grades") {
            composable("grades") { GradesScreen(navController, model) }
            composable("rank") { RankScreen(navController, model) }
        }
    }
}


@Composable
fun LoginDialog() {
    val model: ResultsViewModel = viewModel()
    val yzm = remember { model.yzm }
    AlertDialog(
        onDismissRequest = { ActivityCollector.finishTopActivity() },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(id = R.string.results_dialog_title),
                    color = MaterialTheme.colors.secondary,
                    fontSize = 20.sp
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                )
                CaptchaPic()
                OutlinedTextField(
                    value = yzm.value,
                    onValueChange = { model.yzm.value = it },
                    label = { Text(stringResource(id = R.string.results_dialog_yzm)) },
                    textStyle = TextStyle(color = MaterialTheme.colors.fontHead),
                    maxLines = 1
                )
            }
        },
        confirmButton = {
            OutlinedButton(
                onClick = { model.attemptLogin() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.results_dialog_button))
            }
        }
    )
}


@Composable
fun CaptchaPic() {
    val model: ResultsViewModel = viewModel()
    if (!model.bitmapFlag)
        model.getCaptchaPic()
    val pic = remember { model.bitmap }
    Image(
        modifier = Modifier
            .clickable { model.getCaptchaPic() }
            .size(100.dp),
        bitmap = pic.value,
        contentDescription = "CaptchaPic"
    )
}
