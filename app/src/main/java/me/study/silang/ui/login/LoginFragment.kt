package me.study.silang.ui.login

import android.view.View
import kotlinx.android.synthetic.main.fragment_login.*
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.databinding.FragmentLoginBinding
import me.study.silang.ui.MainActivity
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val layoutId: Int = R.layout.fragment_login

    //
    override fun initView() {
    }

    //

    //臨時的
    fun login() = toMain()

    fun toMain() = MainActivity.launch(activity!!)
}