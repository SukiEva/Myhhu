package github.sukieva.hhu.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import github.sukieva.hhu.R
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.activity.results.ResultsViewModel


@Composable
fun GradesScreen(navController: NavController) {
    val model: ResultsViewModel = viewModel()
    InitView {
        MyScaffold(
            topBar = {
                MaterialTopAppBar(title = stringResource(id = R.string.results_title))
            },
            bottomBar = {
                BottomNav(navController)
            }
        ) {
            Column {
                Text(text = "Welcome!")
            }
        }
    }
}

@Composable
fun RankScreen(navController: NavController) {
    InitView {
        MyScaffold(
            topBar = {
                MaterialTopAppBar(title = stringResource(id = R.string.results_title))
            },
            bottomBar = {
                BottomNav(navController)
            }
        ) {
//            if (!model.isLogin) LoginDialog()
//            else model.getResults()
            Column {
                Text(text = "Second")
            }
        }
    }
}

