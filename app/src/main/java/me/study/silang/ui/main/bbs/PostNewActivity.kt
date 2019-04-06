package me.study.silang.ui.main.bbs

import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_post_new.*
import me.study.silang.R
import me.study.silang.base.activity.BaseActivity
import me.study.silang.databinding.ActivityPostNewBinding
import me.study.silang.ui.MainActivity

class PostNewActivity : BaseActivity<ActivityPostNewBinding>() {
    override val layoutId: Int = R.layout.activity_post_new

    val vm: PostViewModel = PostViewModel()

    override fun initView() {
        editTitle.setEditorFontSize(50)
        Thread(
            Runnable {
                while (save) {
                    Thread.sleep(500)
                    vm.title.set(editTitle.html)
                    vm.content.set(editContent.html)
                }
            }).start()
    }

    fun publish() {
        save = false
        vm.title.set(editTitle.html)
        vm.content.set(editContent.html)
    }

    override fun onRestart() {
        super.onRestart()
        editTitle.html = vm.title.get()
        editContent.html = vm.content.get()
    }

    companion object {
        var save: Boolean = true
    }
}