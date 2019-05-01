package me.study.silang.ui.main.video

import android.content.Context
import android.view.View
import me.study.silang.R
import me.study.silang.base.adapter.BaseBindingAdapter

class VideoImgListAdapter(context: Context) :
    BaseBindingAdapter<String, me.study.silang.databinding.ListItemImgBinding>(context) {
    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.list_item_img
    }
    override fun onBindItem(binding: me.study.silang.databinding.ListItemImgBinding?, item: String?) {
        if (binding != null) {
            binding.imageView.setImageURL(item)
            binding.executePendingBindings()
        }
    }
    interface Callback{
        fun click(v: View)
    }

}