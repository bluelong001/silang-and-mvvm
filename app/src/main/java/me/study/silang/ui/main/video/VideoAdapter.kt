package me.study.silang.ui.main.video

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.ListAdapter
import me.study.silang.R
import me.study.silang.base.adapter.BaseBindingAdapter
import me.study.silang.databinding.ListItemVideoBinding
import me.study.silang.entity.Video

class VideoAdapter(var context: Context) : BaseAdapter() {
    var videoList = ObservableArrayList<VideoModel>()

    fun addAll(model:List<VideoModel>){
        videoList.addAll(model)
        notifyDataSetChanged()
    }
    fun add(model:VideoModel){
        videoList.add(model)
        notifyDataSetChanged()
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var binding: ListItemVideoBinding
        if (convertView == null) {
            binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.list_item_video, parent, false)
            binding.root.tag = binding
            binding.model  =getItem(position)
            return binding.root
        } else {
            binding = convertView.tag as ListItemVideoBinding
        }
        binding.model  =getItem(position)
        return convertView
    }

    override fun getItem(position: Int): VideoModel? {
        return videoList[position]

    }

    override fun getItemId(position: Int):Long  {
        return position.toLong()
    }

    override fun getCount(): Int {
        return videoList.size
    }

}