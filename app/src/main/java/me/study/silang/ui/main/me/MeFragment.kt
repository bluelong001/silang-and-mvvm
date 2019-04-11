package me.study.silang.ui.main.me

import android.content.Intent
import android.content.pm.ActivityInfo
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.component.Glide4Engine
import me.study.silang.databinding.FragmentMeBinding
import me.study.silang.ui.login.LoginActivity
import me.study.silang.ui.main.video.VideoFragment
import org.kodein.di.Kodein
import com.zhihu.matisse.internal.entity.CaptureStrategy
import android.app.Activity
import android.text.TextUtils
import kotlinx.android.synthetic.main.fragment_me.*
import me.study.silang.utils.AnyCallback
import java.net.URI


class MeFragment : BaseFragment<FragmentMeBinding>() {
    override val layoutId: Int = R.layout.fragment_me
    lateinit var vm: MeViewModel

    override fun initView() {
        vm = MeViewModel(mContext)
        vm.initUser(object : AnyCallback() {
            override fun callback() {
                vm.model.get()!!.headIcon.also { icon -> if (!TextUtils.isEmpty(icon)) imageView.setImageURL(icon) }
            }

        })
    }

    fun logout() =
        activity.apply {
            startActivity(Intent(this, LoginActivity::class.java))
            this!!.finish()
        }

    fun setHead() {
        Matisse.from(this@MeFragment)
            .choose(MimeType.ofImage(),true)
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
                        vm.model.get()!!.headIcon.also { icon -> if (!TextUtils.isEmpty(icon)) imageView.setImageURL(icon) }
                    }
                })
            }
        }

    }
}
