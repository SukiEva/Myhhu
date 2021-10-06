package github.sukieva.hhu.ui.activity.results

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import github.sukieva.hhu.data.repository.RemoteRepository
import kotlin.concurrent.thread

class ResultsViewModel : ViewModel() {

    var bitmapFlag: Boolean = false
    var bitmap = mutableStateOf(ImageBitmap(20, 20))

    fun getCaptchaPic() {
        thread {
            RemoteRepository.getCaptchaPic()?.let {
                bitmap.value = it.asImageBitmap()
            }
        }
        bitmapFlag = true
    }


}


