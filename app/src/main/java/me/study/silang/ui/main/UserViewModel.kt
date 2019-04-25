package me.study.silang.ui.main

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.study.silang.http.RetrofitCallback
import me.study.silang.http.RetrofitManager
import me.study.silang.model.UserData
import me.study.silang.model.UserInfo
import me.study.silang.repository.MainRepository
import me.study.silang.utils.AnyCallback

class UserViewModel : ViewModel() {
    var userInfo = ObservableField<UserInfo>()
    var userData = ObservableField<UserData>()

    fun initService(context:Context){

       service= (RetrofitManager.getInstance<MainRepository>(context, MainRepository::class.java).service as MainRepository?)!!
    }
    lateinit var service: MainRepository
//
//    @BindingAdapter("internetUrl")
//    fun setData(img: InternetImageView, data: String) {
//
//    }

    fun initUser(callback: AnyCallback?) {
        service.getUserInfo()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(data: Any?) {
                    userInfo.set(data as UserInfo)
                    callback?.callback()
                }

                override fun onFailure(msg: String?) {

                }
            })
        service.getUserData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(data: Any?) {
                    userData.set(data as UserData)
                    callback?.callback()
                }

                override fun onFailure(msg: String?) {

                }
            })
    }
}
