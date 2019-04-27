package me.study.silang.ui.main.message

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_message.*
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.ui.main.UserViewModel
import me.study.silang.databinding.FragmentMessageBinding

class MessageFragment : BaseFragment<FragmentMessageBinding>() {
    override val layoutId: Int = R.layout.fragment_message
    lateinit var userViewModel: UserViewModel
    @SuppressLint("WrongConstant")
    override fun initView() {
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        list_message.layoutManager = layoutManager
        list_message.adapter=userViewModel.messageListAdapter
        userViewModel.setMsgCallback(object : UserViewModel.MsgEventCallback {
            override fun callback(msg: String){
                hander.obtainMessage().also {
                    message->
                    message.what=1
                    message.obj=msg
                    hander.sendMessage(message)
                }
            }
        })
    }

    var hander: Handler = @SuppressLint("HandlerLeak")
    object:Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if(msg.what==1)
            userViewModel.messageListAdapter.items.add(0,msg.obj as String?)
        }
    }


}
