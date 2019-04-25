package me.study.silang.ui.main.message

import android.text.TextUtils
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProviders
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.component.HeadIconView
import me.study.silang.ui.main.UserViewModel
import me.study.silang.databinding.FragmentMessageBinding

class MessageFragment : BaseFragment<FragmentMessageBinding>() {
    override val layoutId: Int = R.layout.fragment_message
    lateinit var userViewModel: UserViewModel
    override fun initView() {

        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
    }

    companion object {
        @BindingAdapter("imgUrl")
        @JvmStatic
        fun setImgUrl(view: HeadIconView, url: String?) {
            if (!TextUtils.isEmpty(url)) view.setImageURL(url)
        }
    }

}
