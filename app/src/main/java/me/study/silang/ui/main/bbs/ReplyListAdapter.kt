package me.study.silang.ui.main.bbs

import android.content.Context
import android.view.View
import me.study.silang.R
import me.study.silang.base.adapter.BaseBindingAdapter
import me.study.silang.model.ReplyModel

class ReplyListAdapter(context: Context, var callback: ReplyListAdapter.Callback) :
    BaseBindingAdapter<ReplyModel, me.study.silang.databinding.ListItemReplyBinding>(context) {
    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.list_item_reply
    }
    override fun onBindItem(binding: me.study.silang.databinding.ListItemReplyBinding?, item: ReplyModel?) {
        if (binding != null) {
            binding.replyView.setOnClickListener { view->callback.click(view) }
            binding.model=item
            binding.replyView.tag=item
            binding.executePendingBindings()
        }
    }
    interface Callback{
        fun click(v: View)
    }

}