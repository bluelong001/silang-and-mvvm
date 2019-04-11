package me.study.silang.ui.login

import android.preference.PreferenceManager
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.databinding.FragmentLoginBinding
import me.study.silang.http.RetrofitCallback
import me.study.silang.ui.MainActivity
import me.study.silang.ui.login.register.RegisterFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val layoutId: Int = R.layout.fragment_login

    val vm: LoginViewModel = LoginViewModel()
    //
    override fun initView() {
 
    }
    fun signup() {
        activity!!.supportFragmentManager.apply {
            findFragmentById(R.id.navHostFragment) ?: beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fl_login, RegisterFragment())
                .commitAllowingStateLoss()
        }
    }
    fun login() {
//        toMain()
        vm.login(object : RetrofitCallback<String>() {
            override fun onFailure(msg: String?) {

            }

            override fun onSuccess(model: Any?) {
                toMain()
                val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
                sharedPreferences.edit().putString("SiLangToken", model as String?).apply()
            }

        })
    }
    //


    fun toMain() = MainActivity.launch(activity!!)
}