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
import me.study.silang.base.activity.BaseActivity
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.component.Glide4Engine
import me.study.silang.component.HeadIconView
import me.study.silang.databinding.ActivityMeSetBinding
import me.study.silang.databinding.FragmentMeBinding
import me.study.silang.ui.MainActivity
import me.study.silang.ui.login.LoginActivity
import me.study.silang.ui.main.UserViewModel
import me.study.silang.ui.main.video.VideoFragment
import me.study.silang.utils.AnyCallback


class MeCacheVideoActivity : BaseActivity<ActivityMeSetBinding>() {
    override val layoutId: Int = R.layout.fragment_me
    lateinit var vm: MeViewModel
    lateinit var userViewModel: UserViewModel
    override fun initView() {
    }

    fun back(){
        finish()
    }
    companion object {

        fun launch(activity: androidx.fragment.app.FragmentActivity) =
            activity.apply {
                startActivity(Intent(this, MeCacheVideoActivity::class.java))
            }
    }

}
