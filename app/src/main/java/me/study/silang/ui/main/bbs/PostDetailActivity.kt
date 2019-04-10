package me.study.silang.ui.main.bbs

import android.annotation.SuppressLint
import android.view.View
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_post_detail.*
import me.study.silang.base.activity.BaseActivity
import me.study.silang.databinding.ActivityPostDetailBinding
import me.study.silang.model.PostModel
import me.study.silang.model.ReplyModel
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.LinearLayoutManager
import me.study.silang.R


class PostDetailActivity : BaseActivity<ActivityPostDetailBinding>() ,ReplyListAdapter.Callback{
    override fun click(v: View) {
        return
    }

    override val layoutId = R.layout.activity_post_detail

    lateinit var vm: PostViewModel
    @SuppressLint("WrongConstant")
    override fun initView() {
        vm = PostViewModel(this)
        vm.postModel.set(Gson().fromJson(intent.getStringExtra("data"), PostModel::class.java))
        vm.replyListAdapter=ReplyListAdapter(this,this)
        vtitle.loadDataWithBaseURL(null,vm.postModel.get()!!.title,"text/html","utf-8",null)
        vcontent.loadDataWithBaseURL(null,vm.postModel.get()!!.content,"text/html","utf-8",null)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = OrientationHelper.VERTICAL
        list_reply.layoutManager = layoutManager
        list_reply.adapter=vm.replyListAdapter

        vm.initReply(null)
    }
}
