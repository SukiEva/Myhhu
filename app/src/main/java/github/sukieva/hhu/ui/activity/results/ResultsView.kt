package github.sukieva.hhu.ui.activity.results

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.MaterialTopAppBar
import github.sukieva.hhu.ui.components.MyScaffold


@Composable
fun ResultsView() {
    InitView {
        MyScaffold(topBar = { MaterialTopAppBar() }) {
            //CaptchaPic()
        }
    }
}

@Composable
fun CaptchaPic() {
    val model: ResultsViewModel = viewModel()
    if (!model.bitmapFlag)
        model.getCaptchaPic()
    val pic = remember { model.bitmap }
    Image(
        bitmap = pic.value,
        contentDescription = "CaptchaPic"
    )
}

@Composable
@Preview
fun ResultsViewPreview() {
    ResultsView()
}