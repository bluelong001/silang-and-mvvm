package me.study.silang.ui.login.register

import androidx.databinding.ObservableField
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.bean.Param
import me.study.silang.http.RetrofitCallback
import me.study.silang.http.RetrofitManager

class RegisterViewModel : BaseViewModel() {


    val username = ObservableField<String>()
    val password = ObservableField<String>()
    val displayname = ObservableField<String>()
    fun register(callback: RetrofitCallback<String>) {
        RetrofitManager.register(
            Param.build().xput("username", username.get()).xput("password", password.get())
                .xput("displayname", displayname.get()), callback
        )
    }


}
