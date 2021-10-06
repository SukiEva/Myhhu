package github.sukieva.hhu.ui.activity.results

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.sukieva.hhu.data.bean.Course
import github.sukieva.hhu.data.bean.Rank
import github.sukieva.hhu.data.repository.RemoteRepository
import github.sukieva.hhu.utils.ActivityCollector
import github.sukieva.hhu.utils.infoToast
import github.sukieva.hhu.utils.start
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class ResultsViewModel : ViewModel() {

    var bitmapFlag: Boolean = false
    var isLogin: Boolean = false
    var bitmap = mutableStateOf(ImageBitmap(200, 200))
    var yzm = mutableStateOf("")

    var grades: MutableList<Course>? = mutableListOf()
    var rank = Rank("Error", "Error", 999, 5.0, 100.0, 1, 5.0, 100.0, 1)

    fun getResults() {
        viewModelScope.launch {
            grades = RemoteRepository.getGrades()
            rank = RemoteRepository.getRank()
            grades?.forEach {
                println(it.courseName)
            }
            println(rank.name)
        }

    }

    fun attemptLogin() {
        viewModelScope.launch {
            if (RemoteRepository.login(yzm.value)) {
                gotoResults()
            } else getCaptchaPic()
        }
    }

    private fun gotoResults() {
        ActivityCollector.finishTopActivity()
        start<ResultsActivity> {
            putExtra("isLogin", true)
        }
    }

    fun getCaptchaPic() {
        thread {
            RemoteRepository.getCaptchaPic()?.let {
                bitmap.value = it.asImageBitmap()
            }
        }
        bitmapFlag = true
    }


}


