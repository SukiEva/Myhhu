package github.sukieva.hhu.ui.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import github.sukieva.hhu.data.Repository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ResultsViewModel : ViewModel() {
    lateinit var bitmap: Bitmap


    fun getCaptchaPic() {
        viewModelScope.launch {
            bitmap = Repository.getCaptchaPic().first()
        }
    }


}