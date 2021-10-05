package github.sukieva.hhu.ui.activity.favourite

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.sukieva.hhu.MyApp
import github.sukieva.hhu.R
import github.sukieva.hhu.data.entity.Website
import github.sukieva.hhu.data.repository.LocalRepository
import github.sukieva.hhu.utils.ActivityCollector
import github.sukieva.hhu.utils.errorToast
import github.sukieva.hhu.utils.start
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class FavouriteViewModel : ViewModel() {

    private val scope = viewModelScope
    private val URL_VALIDATION_REGEX = "^((https|http)?://).*$"
    var isOpenDialog = mutableStateOf(false)
    var webName = mutableStateOf("")
    var webAddress = mutableStateOf("")
    var websites: List<Website> by mutableStateOf(emptyList())

    fun getWebs() {
        scope.launch {
            LocalRepository.getWebsites().collect {
                websites = it
            }
        }
    }

    fun addWeb() {
        scope.launch {
            val newWeb = Website(webName.value, webAddress.value)
            for (website in websites) {
                if (website.siteName == webName.value) {
                    LocalRepository.updateWeb(newWeb)
                    return@launch
                }
            }
            LocalRepository.insertWeb(newWeb)
        }
    }

    fun resetWeb() {
        scope.launch {
            LocalRepository.resetWebs()
        }
    }

    fun clearDialog() {
        webName.value = ""
        webAddress.value = ""
    }

    fun deleteWebByName(name: String) {
        scope.launch {
            LocalRepository.deleteWebByName(name)
        }
    }

    fun checkUrl(): Boolean {
        if (Pattern.matches(URL_VALIDATION_REGEX, webAddress.value))
            return true
        MyApp.context.getString(R.string.dialog_error_not_valid).errorToast()
        return false
    }

    fun checkNull(): Boolean {
        if (webName.value == "" || webAddress.value == "") {
            MyApp.context.getString(R.string.dialog_error_null).errorToast()
            return false
        }
        return true
    }

    fun refreshUI() {
        start<FavouriteActivity>()
        ActivityCollector.finishTopActivity()
    }
}