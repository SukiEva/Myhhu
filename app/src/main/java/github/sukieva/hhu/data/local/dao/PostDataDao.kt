package github.sukieva.hhu.data.local.dao

import androidx.room.*
import github.sukieva.hhu.data.entity.PostData

@Dao
interface PostDataDao {
    @Insert
    suspend fun insertPostData(postData: PostData): Long

    @Update
    suspend fun updatePostData(newPostData: PostData)

    @Query("select * from PostData")
    suspend fun loadAllPostData(): List<PostData>

    @Delete
    suspend fun deletePostData(postData: PostData)
}