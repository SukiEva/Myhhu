package github.sukieva.hhu.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import github.sukieva.hhu.data.entity.LoginData
import github.sukieva.hhu.data.entity.PostData
import github.sukieva.hhu.data.entity.Website
import github.sukieva.hhu.data.local.dao.FavDao
import github.sukieva.hhu.data.local.dao.LoginDataDao
import github.sukieva.hhu.data.local.dao.PostDataDao

@Database(version = 1, entities = [Website::class, LoginData::class, PostData::class])
abstract class MyAppDatabase : RoomDatabase() {

    abstract fun favDao(): FavDao

    abstract fun loginDataDao(): LoginDataDao

    abstract fun postDataDao(): PostDataDao

    companion object {  // 全局只创建一个数据库实例
        private var instance: MyAppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): MyAppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext, MyAppDatabase::class.java, "app_database")
                .fallbackToDestructiveMigration() // 数据库升级时销毁，测试用
                .build().apply {
                    instance = this
                }
        }
    }

}