package github.sukieva.hhu.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import github.sukieva.hhu.data.entity.Website
import github.sukieva.hhu.data.local.dao.FavDao

@Database(version = 1, exportSchema = false, entities = [Website::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun favDao(): FavDao


    companion object {  // 全局只创建一个数据库实例
        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            }
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database")
                .fallbackToDestructiveMigration() // 数据库升级时销毁，测试用
                .build().apply {
                    instance = this
                }
        }
    }

}