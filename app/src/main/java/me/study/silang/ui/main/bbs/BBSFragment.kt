package me.study.silang.ui.main.bbs

import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.databinding.FragmentBbsBinding
import org.kodein.di.Kodein


class BBSFragment : BaseFragment<FragmentBbsBinding>() {

    override val vm: BBSViewModel = BBSViewModel()
    override val layoutId: Int = R.layout.fragment_bbs


}
