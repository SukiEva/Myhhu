package github.sukieva.hhu.data.repository

import androidx.compose.runtime.mutableStateListOf
import github.sukieva.hhu.MyApp
import github.sukieva.hhu.data.entity.Website
import github.sukieva.hhu.data.local.MyAppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

object LocalRepository {

    private val favDao = MyAppDatabase.getDatabase(MyApp.context).favDao()

    fun getWebsites(): Flow<List<Website>> = flow {
        val websites = favDao.loadAllWebs()
        emit(websites)
    }.flowOn(Dispatchers.IO) // 通过 flowOn 切换到 IO 线程


    suspend fun insertWeb(webSite: Website) = favDao.insertWeb(webSite)

    suspend fun resetWebs() {
        favDao.deleteAllWebs()
        favDao.insertWeb(Website("奥蓝系统", "http://smst.hhu.edu.cn/Mobile/login.aspx"))
        favDao.insertWeb(Website("教务系统", "http://jwxs.hhu.edu.cn/"))
        favDao.insertWeb(Website("信息门户", "http://my.hhu.edu.cn/portal-web/html/index.html"))
    }

}