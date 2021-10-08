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
import github.sukieva.hhu.utils.LogUtil
import github.sukieva.hhu.utils.start
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class ResultsViewModel : ViewModel() {

    var bitmapFlag: Boolean = false
    var isLogin: Boolean = false
    var bitmap = mutableStateOf(ImageBitmap(200, 200))
    var yzm = mutableStateOf("")

    var rank = Rank()

    var termCode = mutableListOf<String>()
    var termGrade = mutableListOf<List<Course>>()
    var isLoaded = mutableStateOf(false)

    private suspend fun separatePages() {
        val grades = RemoteRepository.getGrades()
        val terms: MutableMap<String, MutableList<Course>> = mutableMapOf()
        grades?.let { it ->
            it.sortByDescending {
                it.academicYearCode
            }
            for (grade in it) {
                val termCode = grade.academicYearCode
                var tmp = mutableListOf<Course>()
                if (terms.containsKey(termCode)) {
                    tmp = terms[termCode]!!
                }
                tmp.add(grade)
                terms[termCode] = tmp
            }
        }
        for ((key, value) in terms) {
            termCode.add(key)
            value.sortBy { it.courseAttributeName }
            termGrade.add(value)
        }
    }


    fun getResults() {
        viewModelScope.launch {
            separatePages()
            rank = RemoteRepository.getRank()
            isLoaded.value = true
            LogUtil.d("ResultsActivity", "Successfully get results")
        }
    }

    fun attemptLogin() {
        viewModelScope.launch {
            if (RemoteRepository.login(yzm.value)) {
                gotoResults()
            } else {
                getCaptchaPic()
                yzm.value = ""
            }
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


