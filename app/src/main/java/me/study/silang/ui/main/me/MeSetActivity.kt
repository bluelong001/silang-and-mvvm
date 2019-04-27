package me.study.silang.ui.main.me

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModelProviders
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.internal.entity.CaptureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_me_set.*
import kotlinx.android.synthetic.main.fragment_me.*
import me.study.silang.R
import me.study.silang.base.activity.BaseActivity
import me.study.silang.base.fragment.BaseFragment
import me.study.silang.component.Glide4Engine
import me.study.silang.component.HeadIconView
import me.study.silang.databinding.ActivityMeSetBinding
import me.study.silang.databinding.FragmentMeBinding
import me.study.silang.http.RetrofitCallback
import me.study.silang.http.RetrofitManager
import me.study.silang.model.UserInfo
import me.study.silang.repository.MainRepository
import me.study.silang.ui.MainActivity
import me.study.silang.ui.login.LoginActivity
import me.study.silang.ui.main.UserViewModel
import me.study.silang.ui.main.video.VideoFragment
import me.study.silang.utils.AnyCallback


class MeSetActivity : BaseActivity<ActivityMeSetBinding>() {
    override val layoutId: Int = R.layout.activity_me_set
    lateinit var userInfo:UserInfo
    override fun initView() {
        val bundle = intent.extras
        userInfo= bundle!!.getSerializable("userInfo") as UserInfo
        initService(this)
    }
    private fun initService(context: Context){

        service= (RetrofitManager.getInstance<MainRepository>(context, MainRepository::class.java).service as MainRepository?)!!
    }
    lateinit var service: MainRepository
    var newPass = ObservableField<String>()
    var oldPass = ObservableField<String>()
    @SuppressLint("AutoDispose")
    fun update(){
        service.updatePass(userInfo.id!!,oldPass.get()!!,newPass.get()!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(data: Any?) {
                    AlertDialog.Builder(this@MeSetActivity)
                        .setMessage("修改成功")
                        .setOnDismissListener { dialog-> finish() }
                        .show()
                }

                override fun onFailure(msg: String?) {
                    Toast.makeText(this@MeSetActivity,msg,Toast.LENGTH_LONG).show()
                }
            })
    }
    fun back(){
        finish()
    }
    companion object {

        fun launch(activity: androidx.fragment.app.FragmentActivity , userInfo: UserInfo) {
            var intent = Intent(activity, MeSetActivity::class.java)
            intent.putExtras(Bundle().also { bundle -> bundle.putSerializable("userInfo", userInfo) })
            activity.apply { startActivity(intent) }
        }

    }

}
