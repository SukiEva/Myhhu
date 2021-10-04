package github.sukieva.hhu.ui.activity.results

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.MaterialTopAppBar


@Composable
fun ResultsView() {
    InitView {
        MaterialTopAppBar()
    }
}


@Composable
@Preview
fun ResultsViewPreview() {
    ResultsView()
}