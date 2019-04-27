package me.study.silang.room

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RoomHelper(context: Context) {

    var cache: VideoCacheDataSource

    init {
        cache = Room.databaseBuilder(context, VideoCacheDataSource::class.java, "videoCache").build()
    }

    companion object {
        private var mInstance: RoomHelper? = null

        fun getInstance(context: Context): RoomHelper? {
            synchronized(RoomHelper::class.java) {
                if (mInstance == null) {
                    mInstance = RoomHelper(context)
                }
            }
            return mInstance
        }
    }

    fun download(videoCache: VideoCache) {
        GlobalScope.launch {
            cache.videoCacheDao().download(videoCache)
        }
    }

    fun listVideo(userId: Int, callback: ListVideoSuccess) {
        GlobalScope.launch {
            callback.getList(cache.videoCacheDao().listVideo(userId))
        }
    }

    fun delete(videoCache: VideoCache){
        GlobalScope.launch {
            cache.videoCacheDao().delete(videoCache)
        }
    }

    interface ListVideoSuccess {
        fun getList(list: List<VideoCache>)
    }
}
