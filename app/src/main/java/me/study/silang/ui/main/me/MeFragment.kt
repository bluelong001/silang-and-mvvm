package me.study.silang.ui.main.me

import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.databinding.FragmentMeBinding
import org.kodein.di.Kodein

class MeFragment : BaseFragment<FragmentMeBinding>() {
    override val layoutId: Int = R.layout.fragment_me
    override val kodein: Kodein = Kodein.lazy {
        extend(parentKodein)

    }
}
