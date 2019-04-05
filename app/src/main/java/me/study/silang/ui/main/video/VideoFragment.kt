package me.study.silang.ui.main.video

import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.databinding.FragmentVideoBinding
import org.kodein.di.Kodein

class VideoFragment : BaseFragment<FragmentVideoBinding>(){

    override val layoutId: Int = R.layout.fragment_video

    override val vm: BaseViewModel = VideoViewModel()
}
