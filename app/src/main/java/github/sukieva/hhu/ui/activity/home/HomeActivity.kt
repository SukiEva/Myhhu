package github.sukieva.hhu.ui.activity.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import github.sukieva.hhu.ui.activity.base.BaseActivity
import github.sukieva.hhu.ui.components.SplashScreen

class HomeActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val (appState, setAppState) = remember { mutableStateOf(AppState.Splash) }

            when (appState) {
                AppState.Splash -> {
                    SplashScreen { setAppState(AppState.Home) }
                }
                AppState.Home -> {
                    HomeView()
                }
            }
        }
    }


}


enum class AppState {
    Splash,
    Home
}