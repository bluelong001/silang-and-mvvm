package me.study.silang.ui.main.me

import android.content.Intent
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.databinding.FragmentMeBinding
import me.study.silang.ui.login.LoginActivity
import org.kodein.di.Kodein

class MeFragment : BaseFragment<FragmentMeBinding>() {
    override val layoutId: Int = R.layout.fragment_me
    override val vm:BaseViewModel = MeViewModel()

    fun logout() =
        activity.apply {
            startActivity(Intent(this, LoginActivity::class.java))
            this!!.finish()
        }
}
