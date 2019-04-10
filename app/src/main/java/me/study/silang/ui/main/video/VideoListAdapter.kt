package me.study.silang.ui.main.video

import android.content.Context
import android.media.MediaMetadataRetriever
import android.view.View
import me.study.silang.R
import me.study.silang.base.adapter.BaseBindingAdapter
import me.study.silang.model.VideoModel
import java.util.*

class VideoListAdapter(context: Context, var callback: VideoListAdapter.Callback) :
    BaseBindingAdapter<VideoModel, me.study.silang.databinding.ListItemVideoBinding>(context) {
    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.list_item_video
    }
    override fun onBindItem(binding: me.study.silang.databinding.ListItemVideoBinding?, item: VideoModel?) {
        if (binding != null) {
            binding.videoCard.setOnClickListener { view->callback.click(view) }
            binding.model=item
            binding.videoCard.tag=item
            val media = MediaMetadataRetriever()
            media.setDataSource(item!!.fileUrl!!, Hashtable<String, String>())
            binding.video.setImageBitmap(media.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC))
            binding.executePendingBindings()


        }
    }
    interface Callback{
        fun click(v: View)
    }

}