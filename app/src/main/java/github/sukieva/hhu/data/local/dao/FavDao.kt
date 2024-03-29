package github.sukieva.hhu.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import github.sukieva.hhu.data.entity.Website

@Dao
interface FavDao {

    @Insert
    suspend fun insertWeb(website: Website): Long


    @Query("select * from Website")
    suspend fun loadAllWebs(): List<Website>

    @Query("delete from Website")
    suspend fun deleteAllWebs()

    @Query("delete from Website where site_name = :siteName")
    suspend fun deleteWebBySiteName(siteName: String): Int
}