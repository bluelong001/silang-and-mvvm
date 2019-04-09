package me.study.silang.ui.main.video

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import me.study.silang.R
import me.study.silang.databinding.ListItemVideoBinding
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import me.study.silang.generated.callback.OnClickListener
import me.study.silang.model.VideoModel
import java.util.*


class VideoAdapter(var context: Context,var callback:Callback) : BaseAdapter() {


    var videoList = ObservableArrayList<VideoModel>()

    fun init(model: List<VideoModel>) {
        videoList.clear()
        videoList.addAll(model)
        notifyDataSetChanged()
    }
    fun addAll(model: List<VideoModel>) {
        videoList.addAll(model)
        notifyDataSetChanged()
    }

    fun add(model: VideoModel) {
        videoList.add(model)
        notifyDataSetChanged()
    }

    interface Callback{
        fun click(v:View)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var binding: ListItemVideoBinding
        var view: View
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_item_video, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as ListItemVideoBinding
            view = convertView
        }
        binding.video.setImageBitmap(getImg(videoList[position].fileUrl!!))
        binding.videoCard.tag = videoList[position]
        binding.videoCard.setOnClickListener {view->callback.click(view!!) }
        binding.model = videoList[position]
        return view
    }

    override fun getItem(position: Int): VideoModel? {
        return videoList[position]

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return videoList.size
    }

    fun getImg(url: String): Bitmap {
        val media = MediaMetadataRetriever()
        media.setDataSource(url, Hashtable<String, String>())
        return media.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
    }
}