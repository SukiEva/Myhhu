package github.sukieva.hhu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import github.sukieva.hhu.ui.viewmodel.ResultsViewModel


@Composable
fun CaptchaPic() {
    var image by remember {
        mutableStateOf(ImageBitmap(20, 20))
    }
    val model: ResultsViewModel = viewModel()
    Image(
        bitmap = image,
        contentDescription = "CaptchaPic",
        modifier = Modifier.clickable {
            image = model.bitmap.asImageBitmap()
        }
    )
}

@Preview
@Composable
fun CaptchaPicPreview() {
    CaptchaPic()
}