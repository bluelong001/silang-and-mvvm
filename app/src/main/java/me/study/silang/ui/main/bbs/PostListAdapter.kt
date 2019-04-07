package me.study.silang.ui.main.bbs

import android.content.Context
import me.study.silang.R
import me.study.silang.base.adapter.BaseBindingAdapter
import me.study.silang.entity.Post

class PostListAdapter(context: Context) :
    BaseBindingAdapter<Post, me.study.silang.databinding.ListItemPostBinding>(context) {
    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.list_item_post
    }

    override fun onBindItem(binding: me.study.silang.databinding.ListItemPostBinding?, item: Post?) {
        if (binding != null) {
            binding.postBean=item
            binding.executePendingBindings()
        }
    }

}