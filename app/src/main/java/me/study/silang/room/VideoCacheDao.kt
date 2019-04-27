package me.study.silang.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VideoCacheDao {
    @Query("SELECT * FROM VideoCache WHERE :userId")
    fun listVideo(userId: Int): List<VideoCache>

    @Insert
    fun download(videoCache: VideoCache)

    @Delete
    fun delete(videoCache: VideoCache)
}
