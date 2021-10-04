package github.sukieva.hhu.ui.activity.results

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import github.sukieva.hhu.data.repository.RemoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlin.concurrent.thread

class ResultsViewModel : ViewModel() {

    val picFlow = MutableStateFlow(ImageBitmap(100, 100))
    lateinit var bitmap: ImageBitmap

    init {
        getPic()
    }

    fun getPic() {
        thread {
            bitmap = RemoteRepository.getBitmap().asImageBitmap()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun fectchCaptchaPic(name: String) =
        RemoteRepository.getCaptchaPic()
            .onStart {

            }
            .catch {
                // 捕获上游出现的异常
            }
            .onCompletion {

            }.asLiveData()


}


