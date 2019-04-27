package me.study.silang.ui.main.message

import android.content.Context
import android.view.View
import me.study.silang.R
import me.study.silang.base.adapter.BaseBindingAdapter
import me.study.silang.databinding.ListItemMessageBinding
import me.study.silang.model.PostModel

class MessageListAdapter(context: Context) :
    BaseBindingAdapter<String, ListItemMessageBinding>(context) {
    override fun getLayoutResId(viewType: Int): Int {
        return R.layout.list_item_message
    }
    override fun onBindItem(binding:ListItemMessageBinding?, item: String?) {
        if (binding != null) {
            binding.executePendingBindings()
        }
    }

}