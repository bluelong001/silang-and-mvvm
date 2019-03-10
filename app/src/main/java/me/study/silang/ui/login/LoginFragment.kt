package me.study.silang.ui.login

import android.view.View
import kotlinx.android.synthetic.main.fragment_login.*
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.databinding.FragmentLoginBinding
import me.study.silang.ui.MainActivity
import me.study.silang.ui.main.mainKodeinModule
import org.kodein.di.Kodein
import org.kodein.di.generic.instance

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override val layoutId: Int = R.layout.fragment_login

    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)
        import(mainKodeinModule)
    }
    val viewModel: LoginViewModel by instance()
    //
    override fun initView() {
        btn_logon.setOnClickListener { View.OnClickListener { login() } }
//        viewModel.userInfo
//                .toReactiveStream()
//                .doOnNext { toMain() }
//                .autoDisposable(scopeProvider)
//                .subscribe()
////
//        viewModel.loadingLayout
//                .toReactiveStream()
//                .doOnNext { loadingViewModel.applyState(it) }
//                .autoDisposable(scopeProvider)
//                .subscribe()
    }

    //
//    fun login() = viewModel.login()
//
    //臨時的
    fun login() = toMain()

    fun toMain() = MainActivity.launch(activity!!)
}