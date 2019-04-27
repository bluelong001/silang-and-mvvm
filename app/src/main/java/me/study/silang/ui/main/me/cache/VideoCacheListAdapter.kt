package me.study.silang.ui.main.me.cache

import android.content.Context
import android.media.MediaMetadataRetriever
import android.view.View
import me.study.silang.R
import me.study.silang.base.adapter.BaseBindingAdapter
import me.study.silang.databinding.ListItemVideoCacheBinding
import me.study.silang.room.RoomHelper
import me.study.silang.room.VideoCache
import java.util.*

class VideoCacheListAdapter(context: Context) :
    BaseBindingAdapter<VideoCache, ListItemVideoCacheBinding>(context) {
    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.list_item_video_cache
    }

    override fun onBindItem(binding: ListItemVideoCacheBinding?, item: VideoCache?) {
        if (binding != null) {
            binding.videoCard.setOnClickListener {
                MeCacheVideoShowActivity.launch(context, item!!.localUrl)
            }
//            binding.model=item
            binding.btnDelete.setOnClickListener {
                RoomHelper.getInstance(context)!!.delete(item!!)
            }
            binding.videoCard.tag = item
            binding.executePendingBindings()


        }
    }

    interface Callback {
        fun click(v: View)
    }

}