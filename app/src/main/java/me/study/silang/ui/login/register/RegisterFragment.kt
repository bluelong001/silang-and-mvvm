package me.study.silang.ui.login.register

import android.preference.PreferenceManager
import android.widget.Toast
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.databinding.FragmentRegisterBinding
import me.study.silang.http.RetrofitCallback
import me.study.silang.ui.MainActivity
import me.study.silang.ui.login.LoginActivity
import me.study.silang.ui.login.LoginFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override val layoutId: Int = R.layout.fragment_register

    val vm: RegisterViewModel = RegisterViewModel()
    //
    override fun initView() {

    }

    fun signin() {
        activity!!.supportFragmentManager.apply {
            findFragmentById(R.id.navHostFragment) ?: beginTransaction()
                .replace(R.id.fl_login, LoginFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        }
    }

    fun register() {

        vm.register(object : RetrofitCallback<String>() {
            override fun onFailure(msg: String?) {
                Toast.makeText(context,"register fail",Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(model: Any?) {
                signin()
            }

        })
    }
//
}