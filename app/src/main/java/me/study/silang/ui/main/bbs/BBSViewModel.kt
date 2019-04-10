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
import me.study.silang.model.PostModel
import me.study.silang.repository.BBSRepository
import me.study.silang.utils.AnyCallback

class BBSViewModel(val context: Context) : BaseViewModel() {
    var page = Page()
    var service: BBSRepository =
        (RetrofitManager.getInstance<BBSRepository>(context, BBSRepository::class.java).service as BBSRepository?)!!

    lateinit var postListAdapter: PostListAdapter

    fun listMorePostList(callback:AnyCallback?) {

        service.list(Param().page(page.page).pageSize(page.pageSize))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(model: Any?) {
                    postListAdapter.items.addAll(model as List<PostModel>)

                    callback?.callback()
                }

                override fun onFailure(msg: String?) {
                    page.back()
                }
            })
        page.next()
    }

    fun initPostList(callback:AnyCallback?) {
        page=Page()

        service.list(Param().page(page.page).pageSize(page.pageSize))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(model: Any?) {
                    postListAdapter.items.clear()
                    postListAdapter.items.addAll(model as List<PostModel>)

                    callback?.callback()
                }

                override fun onFailure(msg: String?) {
                    page.back()
                }
            })
        page.next()
    }

    fun reset(callback:AnyCallback?) {
        page=Page()

        service.list(Param().page(page.page).pageSize(page.pageSize))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onSuccess(model: Any?) {
                    postListAdapter.items.clear()
                    postListAdapter.items.addAll(model as List<PostModel>)
                    callback?.callback()
                }

                override fun onFailure(msg: String?) {
                    page.back()
                }
            })
        page.next()
    }



}



