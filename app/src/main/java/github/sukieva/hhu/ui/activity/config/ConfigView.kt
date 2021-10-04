package github.sukieva.hhu.ui.activity.config

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import github.sukieva.hhu.ui.activity.base.InitView
import github.sukieva.hhu.ui.components.MaterialTopAppBar


@Composable
fun ConfigView() {
    InitView {
        MaterialTopAppBar()
    }
}


@Preview
@Composable
fun ConfigViewPreview() {
    ConfigView()
}