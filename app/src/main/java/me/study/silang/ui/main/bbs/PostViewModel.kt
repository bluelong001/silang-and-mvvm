package me.study.silang.ui.main.bbs

import android.content.Context
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.study.silang.base.videomodel.BaseViewModel
import me.study.silang.bean.Param
import me.study.silang.entity.Post
import me.study.silang.entity.Reply
import me.study.silang.http.RetrofitCallback
import me.study.silang.http.RetrofitManager
import me.study.silang.model.PostModel
import me.study.silang.model.ReplyModel
import me.study.silang.repository.PostRepository
import me.study.silang.utils.AnyCallback
import me.study.silang.utils.LogUtils

class PostViewModel(var context: Context) : BaseViewModel() {
    private val TAG = "PostViewModel"
    var post = ObservableField<Post>()
    var textReply = ObservableField<String>()

    var postModel = ObservableField<PostModel>()

    lateinit var replyListAdapter: ReplyListAdapter
    var service: PostRepository =
        (RetrofitManager.getInstance<PostRepository>(context, PostRepository::class.java).service as PostRepository?)!!

    fun insertPost(callback: RetrofitCallback<Any>) {
        service.add(Param().putObj(post.get()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(callback)
    }

    fun initReply(callback: AnyCallback?) {
        service.list(Param.build().xput("postId", postModel.get()!!.id!!))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onFailure(msg: String?) {
                    if (msg != null) {
                        LogUtils.e(TAG, msg)
                    }
                }

                override fun onSuccess(model: Any?) {
                    replyListAdapter.items.clear()
                    replyListAdapter.items.addAll(model as List<ReplyModel>)
                    callback?.callback()
                }

            })
    }

    fun insertReply() {
        service.addReply(Param.build().putObj(Reply().also { reply ->
            reply.context = textReply.get()
            reply.postId = postModel.get()!!.id
        }))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : RetrofitCallback<Any>() {
                override fun onFailure(msg: String?) {
                    if (msg != null) {
                        LogUtils.e(TAG, msg)
                    }
                }

                override fun onSuccess(model: Any?) {
                    textReply.set("")

                    initReply(null)
//                    callback?.callback()
                }

            })
    }
//    fun loadMoreReply(callback: AnyCallback?) {
//        service.list(Param.build().xput("id", postModel.get()!!.id!!))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(object : RetrofitCallback<Any>() {
//                override fun onFailure(msg: String?) {
//                    if (msg != null) {
//                        LogUtils.e(TAG, msg)
//                    }
//                }
//
//                override fun onSuccess(model: Any?) {
//                    callback?.callback()
//                }
//
//            })
//    }
}
