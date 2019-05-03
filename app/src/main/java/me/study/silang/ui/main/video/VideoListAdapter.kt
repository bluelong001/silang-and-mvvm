package me.study.silang.ui.main.video

import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import android.view.View
import me.study.silang.R
import me.study.silang.base.adapter.BaseBindingAdapter
import me.study.silang.model.VideoModel
import me.study.silang.utils.MediaUtils
import java.util.*

class VideoListAdapter(context: Context) :
    BaseBindingAdapter<VideoModel, me.study.silang.databinding.ListItemVideoBinding>(context) {
    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.list_item_video
    }

    override fun onBindItem(binding: me.study.silang.databinding.ListItemVideoBinding?, item: VideoModel?) {
        if (binding != null) {
            binding.videoCard.setOnClickListener { view ->
                VideoDetailActivity.launch(
                    (view.tag as VideoModel),
                    context
                )
            }
            binding.model = item
            binding.videoCard.tag = item
//            binding.video.set
            binding.executePendingBindings()


        }
    }
}