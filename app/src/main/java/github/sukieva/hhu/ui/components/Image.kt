package github.sukieva.hhu.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter


@ExperimentalCoilApi
@Composable
fun CaptchaPic() {
    val painter =
        rememberImagePainter(data = "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201501%2F27%2F20150127103509_KvXhU.jpeg&refer=http%3A%2F%2Fb-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1631501719&t=9653a6a5bb4e29505b9b582c770b42ef",
            builder = {
                crossfade(true)
            })
    Image(
        painter = painter,
        contentDescription = "CaptchaPic"
    )
}

@Preview
@Composable
fun CaptchaPicPreview() {
    CaptchaPic()
}