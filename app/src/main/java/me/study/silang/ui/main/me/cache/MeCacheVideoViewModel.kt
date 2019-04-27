package me.study.silang.ui.main.me.cache

import android.content.Context
import androidx.lifecycle.ViewModel
import me.study.silang.room.RoomHelper
import me.study.silang.room.VideoCache
import me.study.silang.room.VideoCacheDataSource

class MeCacheVideoViewModel(var context:Context,var userId: Int) : ViewModel(){
    var videoCacheAdapter :VideoCacheListAdapter = VideoCacheListAdapter(context)
    var dataSource = RoomHelper.getInstance(context)!!
    fun initVideoCache(){
        dataSource.listVideo(userId,object :RoomHelper.ListVideoSuccess{
            override fun getList(list: List<VideoCache>) {
                videoCacheAdapter.items.addAll(list)
            }

        })

    }


}
