package me.study.silang.ui.main.me

import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.text.TextUtils
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModelProviders
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.fragment_me.*
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.component.Glide4Engine
import me.study.silang.component.HeadIconView
import me.study.silang.databinding.FragmentMeBinding
import me.study.silang.ui.login.LoginActivity
import me.study.silang.ui.main.UserViewModel
import me.study.silang.ui.main.bbs.BBSFragment
import me.study.silang.ui.main.video.VideoFragment
import me.study.silang.utils.AnyCallback



class MeFragment : BaseFragment<FragmentMeBinding>() {
    override val layoutId: Int = R.layout.fragment_me
    lateinit var vm: MeViewModel
    lateinit var userViewModel: UserViewModel
    override fun initView() {
        vm = MeViewModel(mContext)
        userViewModel = ViewModelProviders.of(activity!!).get(UserViewModel::class.java)
    }

    fun logout() =
        activity.apply {
            startActivity(Intent(this, LoginActivity::class.java))
            this!!.finish()
        }

    fun showCacheVideo(){
        MeCacheVideoActivity.launch(activity!!)
    }

    fun modify() {
        MeSetActivity.launch(activity!!,userViewModel.userInfo.get()!!)
    }

    fun setHead() {
        Matisse.from(this@MeFragment)
            .choose(MimeType.ofImage(), true)
            .countable(true)
            .capture(true)
            .captureStrategy(CaptureStrategy(true, "me.study.silang.fileprovider"))
            .maxSelectable(1)
//            .showSingleMediaType(true)
            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
            .thumbnailScale(0.85f)
            .imageEngine(Glide4Engine())
            .forResult(VideoFragment.REQUEST_CODE_CHOOSE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == VideoFragment.REQUEST_CODE_CHOOSE) {

                vm.headImgUrl.set(Matisse.obtainPathResult(intent!!)[0])
                vm.updateHead(object : AnyCallback() {
                    override fun callback() {
                        userViewModel.initUser(null)
                    }
                })
            }
        }

    }
}
