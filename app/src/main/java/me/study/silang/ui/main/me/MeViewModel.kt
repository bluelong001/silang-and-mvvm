package me.study.silang.ui.main.me

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.zhihu.matisse.internal.utils.PathUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.bean.Param
import me.study.silang.component.InternetImageView
import me.study.silang.entity.User
import me.study.silang.entity.Video
import me.study.silang.http.RetrofitCallback
import me.study.silang.http.RetrofitManager
import me.study.silang.model.UserData
import me.study.silang.model.UserInfo
import me.study.silang.repository.MeRepository
import me.study.silang.repository.PostRepository
import me.study.silang.ui.main.video.VideoViewModel
import me.study.silang.utils.AnyCallback
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.ArrayList

class MeViewModel(var context: Context) : BaseViewModel() {
    //    var model = ObservableField<UserInfo>()
//    var userData = ObservableField<UserData>()
    var headImgUrl = ObservableField<String>()
    var signature = ObservableField<String>()
    var service: MeRepository =
        (RetrofitManager.getInstance<MeRepository>(context, MeRepository::class.java).service as MeRepository?)!!
//
//    @BindingAdapter("internetUrl")
//    fun setData(img: InternetImageView, data: String) {
//
//    }

    fun updateSignature(sign: String, callback: AnyCallback?) {
        Param().also { param ->
            param.put("signature", sign)
            service.updateSignature(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : RetrofitCallback<Any>() {
                    override fun onSuccess(data: Any?) {
                        callback?.callback()
                        signature.set(sign)

                    }

                    override fun onFailure(msg: String?) {
                    }

                })
        }
    }

    fun updateHead(callback: AnyCallback?) {
        val out = File(headImgUrl.get()!!)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), out)
        val file = MultipartBody.Part.createFormData("file", out.name, requestFile)
        val map: HashMap<String, String> = HashMap()
        map["type"] = "jpg"
        val files = ArrayList<MultipartBody.Part>()
        files.add(file)
        service.upload(map, files)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(data: Any?) {
                    User().also { user ->
                        user.fileId = Integer.parseInt(data as String)
                        service.updateHead(Param().putObj(user))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(object : RetrofitCallback<Any>() {
                                override fun onSuccess(data: Any?) {
                                    callback?.callback()

                                }

                                override fun onFailure(msg: String?) {
                                }

                            })

                    }
                }

                override fun onFailure(msg: String?) {

                }
            })

    }


    // TODO: Implement the ViewModel
}
