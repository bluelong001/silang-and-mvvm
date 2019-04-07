package me.study.silang.ui.main.bbs

import android.content.Context
import android.view.View
import me.study.silang.R
import me.study.silang.base.adapter.BaseBindingAdapter
import me.study.silang.entity.Post
import me.study.silang.ui.main.video.VideoAdapter

class PostListAdapter(context: Context,var callback: PostListAdapter.Callback) :
    BaseBindingAdapter<PostModel, me.study.silang.databinding.ListItemPostBinding>(context) {
    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.list_item_post
    }
    override fun onBindItem(binding: me.study.silang.databinding.ListItemPostBinding?, item: PostModel?) {
        if (binding != null) {
            binding.postView.setOnClickListener { view->callback.click(view) }
            binding.model=item
            binding.executePendingBindings()
        }
    }
    interface Callback{
        fun click(v: View)
    }

}