package github.sukieva.hhu.data.local.dao

import androidx.room.*
import github.sukieva.hhu.data.entity.LoginData

@Dao
interface LoginDataDao {
    @Insert
    suspend fun insertLoginData(loginData: LoginData): Long

    @Update
    suspend fun updateLoginData(newLoginData: LoginData)

    @Query("select * from LoginData")
    suspend fun loadAllLoginData(): List<LoginData>

    @Delete
    suspend fun deleteLoginData(loginData: LoginData)
}