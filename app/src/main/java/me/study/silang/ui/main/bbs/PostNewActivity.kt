package me.study.silang.ui.main.bbs

import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_post_new.*
import me.study.silang.R
import me.study.silang.base.activity.BaseActivity
import me.study.silang.databinding.ActivityPostNewBinding
import me.study.silang.entity.Post
import me.study.silang.http.RetrofitCallback
import me.study.silang.ui.MainActivity
import me.study.silang.utils.LogUtils

class PostNewActivity : BaseActivity<ActivityPostNewBinding>() {
    override val layoutId: Int = R.layout.activity_post_new

    lateinit var vm: PostViewModel

    override fun initView() {
        vm = PostViewModel(this)
        editTitle.setEditorFontSize(50)
        Thread(
            Runnable {
                while (save) {
                    Thread.sleep(500)
                    Post().also { post ->
                        post.title = editTitle.html
                        post.content = editContent.html
                        vm.post.set(post)
                    }
                }
            }).start()
    }

    fun publish() {
        save = false
        Post().also { post ->
            post.title = editTitle.html
            post.content = editContent.html
            vm.post.set(post)
        }
        vm.insertPost(object : RetrofitCallback<Any>() {
            override fun onSuccess(model: Any?) {
                finish()
            }

            override fun onFailure(msg: String?) {
            }


        })
    }

    override fun onRestart() {
        super.onRestart()
        editTitle.html = vm.post.get()!!.title
        editContent.html = vm.post.get()!!.content
    }

    companion object {
        var save: Boolean = true
    }
}