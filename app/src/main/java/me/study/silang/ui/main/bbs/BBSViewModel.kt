package me.study.silang.ui.main.bbs

import android.content.Context
import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.bean.Page
import me.study.silang.bean.Param
import me.study.silang.entity.Post
import me.study.silang.http.RetrofitCallback
import me.study.silang.http.RetrofitManager
import me.study.silang.repository.BBSRepository
import me.study.silang.ui.main.video.VideoModel

class BBSViewModel(val context: Context) : BaseViewModel() {
    var page = ObservableField<Page>()

    var service: BBSRepository =
        (RetrofitManager.getInstance<BBSRepository>(context, BBSRepository::class.java).service as BBSRepository?)!!

    lateinit var postListAdapter:PostListAdapter

    fun initPostList() {
        service.list(Param().page(0).pageSize(10))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(model: Any?) {
                    page.set(Page())
                    postListAdapter.items.clear()
                    postListAdapter.items.addAll(model as List<PostModel>)
                    Page(page.get()!!.page + page.get()!!.pageSize, page.get()!!.pageSize + 10)
                        .also { p -> page.set(p) }
                }

                override fun onFailure(msg: String?) {

                }
            })
    }



}
