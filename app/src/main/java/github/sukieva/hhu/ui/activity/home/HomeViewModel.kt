package github.sukieva.hhu.ui.activity.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.sukieva.hhu.service.CheckInService
import github.sukieva.hhu.utils.DataManager
import github.sukieva.hhu.utils.getDate
import github.sukieva.hhu.utils.infoToast
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    var isTodayCheckIn = mutableStateOf(false)


    fun getCheckInStatus() {
        viewModelScope.launch {
            val date = getDate()
            val lastCheckDate = DataManager.readData("whenCheckIn", "")
            isTodayCheckIn.value = (date == lastCheckDate)
        }
    }


    fun checkIn() {
        "打卡中...".infoToast()
        viewModelScope.launch {
            CheckInService.checkIn()
            getCheckInStatus()
        }
    }
}