package me.study.silang.ui.main.bbs

import android.content.Context
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.http.RetrofitManager
import me.study.silang.repository.BBSRepository

class BBSViewModel(val context: Context) : BaseViewModel() {
    var service: BBSRepository =
        (RetrofitManager.getInstance<BBSRepository>(context, BBSRepository::class.java).service as BBSRepository?)!!



}
