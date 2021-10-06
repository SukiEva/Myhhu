package github.sukieva.hhu.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import github.sukieva.hhu.R
import github.sukieva.hhu.ui.activity.base.InitView


@Composable
fun GradesScreen() {
    Column {
        Text(text = "Welcome!")
    }
}

@Composable
fun RankScreen() {
    Column {
        Text(text = "Second screen!")
    }
}

@Preview
@Composable
fun BottomSheetNavPreview() {
    GradesScreen()
}
