package me.study.silang.ui.main.bbs

import android.app.Activity
import android.content.Intent
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.databinding.FragmentBbsBinding
import org.kodein.di.Kodein


class BBSFragment : BaseFragment<FragmentBbsBinding>() {

    lateinit var vm: BBSViewModel
    override val layoutId: Int = R.layout.fragment_bbs


    override fun initView() {
        vm= BBSViewModel(mContext)
    }
    fun newPost() {
        Intent(activity, PostNewActivity::class.java).also { intent ->
            startActivityForResult(intent, REQUEST_CODE_NEW_POST)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_CODE_NEW_POST && resultCode == Activity.RESULT_OK) {
        }

    }

    companion object {
        const val REQUEST_CODE_NEW_POST = 100
    }
}
