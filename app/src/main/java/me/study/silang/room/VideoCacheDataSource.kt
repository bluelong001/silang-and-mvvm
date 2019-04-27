package me.study.silang.room

import androidx.room.Database
import androidx.room.RoomDatabase
import me.study.silang.entity.Video

@Database(entities = [VideoCache::class], version = 1)
abstract class VideoCacheDataSource : RoomDatabase() {
    abstract fun videoCacheDao(): VideoCacheDao

}
