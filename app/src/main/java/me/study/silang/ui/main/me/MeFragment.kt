package me.study.silang.ui.main.me

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import me.study.silang.R
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.component.Glide4Engine
import me.study.silang.databinding.FragmentMeBinding
import me.study.silang.ui.login.LoginActivity
import me.study.silang.ui.main.UserViewModel
import me.study.silang.ui.main.me.cache.MeCacheVideoActivity
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

    fun logout() {
        userViewModel.mSocket.run {
            this!!.emit("logout", userViewModel.userInfo.get()!!.id)
            this.disconnect()
        }
        activity.apply {
            Intent(this, LoginActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                this!!.finish()
            }
        }
    }

    fun showCacheVideo() {
        MeCacheVideoActivity.launch(activity!!, userViewModel.userInfo.get()!!.id!!)
    }

    fun modify() {
        MeSetActivity.launch(activity!!, userViewModel.userInfo.get()!!)
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

    fun setSignature() {
        val mEdit = EditText(mContext)
        var dialog = AlertDialog.Builder(mContext)
            .setTitle("修改你的个性签名")
            .setView(mEdit)
            .setPositiveButton(
                "确认输入"
            ) { dialog, which -> run{vm.updateSignature(mEdit.text.toString(), null)
            userViewModel.initUser(null,mContext )}}
            .setNegativeButton(
                "取消输入"
            ) { dialog, which -> dialog.cancel() }
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == VideoFragment.REQUEST_CODE_CHOOSE) {
                vm.headImgUrl.set(Matisse.obtainPathResult(intent!!)[0])
                vm.updateHead(object : AnyCallback() {
                    override fun callback() {
                        userViewModel.initUser(null, mContext)
                    }
                })
            }
            else if(requestCode == MeSetActivity.REQUEST_CODE_SET){
                userViewModel.initUser(null,mContext)
            }
        }

    }
}
