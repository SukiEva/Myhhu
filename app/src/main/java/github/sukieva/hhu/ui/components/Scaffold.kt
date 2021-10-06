package github.sukieva.hhu.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable


@Composable
fun MyScaffold(
    topBar: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        content = {
            Column {
                content()
            }
        }
    )
}