package me.study.silang.ui.login

import androidx.databinding.ObservableField
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.http.RetrofitCallback
import me.study.silang.http.RetrofitManager

class LoginViewModel : BaseViewModel() {


    val username = ObservableField<String>()
    val password = ObservableField<String>()

    fun login(callback: RetrofitCallback<String>) {

        RetrofitManager.login(
            username.get(), password.get(), callback
        )
    }


}
