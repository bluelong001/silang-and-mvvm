package me.study.silang.ui.main.message

import android.content.Context
import android.view.View
import me.study.silang.R
import me.study.silang.base.adapter.BaseBindingAdapter
import me.study.silang.databinding.ListItemMessageBinding
import me.study.silang.entity.Message
import me.study.silang.model.PostModel

class MessageListAdapter(context: Context, var callback: Callback) :
    BaseBindingAdapter<Message, ListItemMessageBinding>(context) {
    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.list_item_message
    }

    override fun onBindItem(binding: ListItemMessageBinding?, item: Message?) {
        if (binding != null) {
            binding.btonClose.setOnClickListener { callback.del(item!!) }
            binding.executePendingBindings()
        }
    }

    interface Callback {
        fun del(msg: Message)
    }
}