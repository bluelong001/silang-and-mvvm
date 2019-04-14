package me.study.silang.ui.login

import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_login.*
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.databinding.FragmentLoginBinding
import me.study.silang.http.RetrofitCallback
import me.study.silang.ui.MainActivity
import me.study.silang.ui.login.register.RegisterFragment

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val layoutId: Int = R.layout.fragment_login
    private val TAG = "RetrofitCallback"
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
        cl_login.visibility= View.INVISIBLE
        progressBar.visibility=View.VISIBLE
//        toMain()
        vm.login(object : RetrofitCallback<String>() {
            override fun onFailure(msg: String?) {
                cl_login.visibility= View.VISIBLE
                progressBar.visibility=View.INVISIBLE
                Toast.makeText(mContext,"账号名或密码错误！",Toast.LENGTH_LONG).show()
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